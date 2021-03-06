package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.common.constant.Constant;
import com.amber.foodie.common.utils.DateUtil;
import com.amber.foodie.common.utils.JsonUtil;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.foodie.service.*;
import com.amber.foodie.mapper.OrdersMapper;
import com.amber.foodie.pojo.*;
import com.amber.foodie.pojo.bo.ShopcartBO;
import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.enums.OrderStatusEnum;
import com.amber.foodie.pojo.enums.YesOrNo;
import com.amber.foodie.pojo.vo.MerchantOrdersVO;
import com.amber.foodie.pojo.vo.OrderVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    AddressService addressService;

    @Autowired
    ItemsImgService itemsImgService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemsSpecService itemsSpecService;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 创建订单
     *
     * @param submitOrderBO
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        Integer payMethod = submitOrderBO.getPayMethod();
        String addressId = submitOrderBO.getAddressId();

        String leftMsg = submitOrderBO.getLeftMsg();
        String userId = submitOrderBO.getUserId();
        // 默认邮费为0
        Integer postAmount = 0;
        // 1. 向订单表插入数据 Orders Order_Items Order_Status
        Orders orders = new Orders();
        String orderId = UUID.randomUUID().toString().replace("-", "");
        orders.setId(orderId);
        orders.setUserId(userId);
        // 查找地址
        UserAddress userAddress = addressService.findUserAddressById(addressId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());

        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String[] itemsSpecIds = itemSpecIds.split(",");
        Integer totalAmount = 0;    // 商品原价累计
        Integer realPayAmount = 0;  // 优惠后的实际支付价格累计
        String key = Constant.FOOID_SHOPCART + Constant.COLOL + userId;
        String value = redisUtils.getCacheObject(key);
        List<ShopcartBO> shopcartBOS = JsonUtil.jsonToList(value, ShopcartBO.class);
        // 需要从购物车删除的数据
        List<ShopcartBO> needBeRemove = new ArrayList<>();
        for (String itemsSpecId : itemsSpecIds) {

            // 整合redis 商品数量会从redis中获取
            ShopcartBO buyCountsFtomShopCart = getBuyCountsFtomShopCart(shopcartBOS, itemsSpecId);
            int buyCounts = buyCountsFtomShopCart.getBuyCounts();
            needBeRemove.add(buyCountsFtomShopCart);
            ItemsSpec itemsSpec = itemsSpecService.findById(itemsSpecId);
            OrderItems orderItems = new OrderItems();
            String orderItemsId = UUID.randomUUID().toString();
            orderItems.setId(orderItemsId);
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemsSpec.getItemId());

            orders.setIsComment(YesOrNo.No.type);
            orders.setIsDelete(YesOrNo.No.type);
            orders.setCreatedTime(new Date());

            // 查找主图
            ItemsImg itemsImg = itemsImgService.findMainImgByItemId(itemsSpec.getItemId());
            String url = itemsImg != null ? itemsImg.getUrl() : null;
            if (StringUtils.isNoneBlank(url)) {
                orderItems.setItemImg(itemsImg.getUrl());
            }
            Items items = itemService.queryItemById(itemsSpec.getItemId());
            orderItems.setItemName(items.getItemName());
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItems.setBuyCounts(buyCounts);
            orderItemService.createOrderItem(orderItems);

            int result = itemsSpecService.decreaseItemSpecStock(itemsSpecId, buyCounts);
            if (result != 1) {
                throw new RuntimeException("订单创建失败，原因：库存不足!");
            }
        }
        // 订单总价格
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        orders.setUpdatedTime(new Date());
        ordersMapper.insert(orders);

        // 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);

        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusService.create(waitPayOrderStatus);

        //4构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + orders.getPostAmount());
        merchantOrdersVO.setPayMethod(payMethod);

        //5构建自定义订单
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        shopcartBOS.removeAll(needBeRemove);
        // 刷新缓存
        redisUtils.setCacheObject(key, JsonUtil.toJson(shopcartBOS));
        return orderVO;
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer type) {
        OrderStatus update = new OrderStatus();
        update.setOrderId(merchantOrderId);
        update.setOrderStatus(type);
        update.setPayTime(new Date());
        orderStatusService.update(update);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void notifyMerchantOrderPaid(String merchantOrderId) {
        updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        // 查询未支付订单
        List<OrderStatus> orderStatusList =
                orderStatusService.findAllByStatus(OrderStatusEnum.WAIT_PAY.type);
        for (OrderStatus orderStatus : orderStatusList) {
            Date createdTime = orderStatus.getCreatedTime();
            if (DateUtil.daysBetween(createdTime, new Date()) >= 1) {
                //当订单时间超过一天未支付 关闭
                orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
                orderStatus.setCloseTime(new Date());
                orderStatusService.update(orderStatus);
            }
        }
    }

    private ShopcartBO getBuyCountsFtomShopCart(List<ShopcartBO> shopcartBOS, String specId) {
        for (ShopcartBO shopcartBO : shopcartBOS) {
            if (shopcartBO.getSpecId().equals(specId)) {
                return shopcartBO;
            }
        }
        return null;
    }
}
