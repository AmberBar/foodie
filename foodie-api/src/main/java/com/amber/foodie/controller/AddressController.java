package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.MobileEmailUtils;
import com.amber.foodie.foodie.service.AddressService;
import com.amber.foodie.pojo.UserAddress;
import com.amber.foodie.pojo.bo.AddressBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(value = "地址管理", tags = {"地址相關api接口"})
public class AddressController {

    @Autowired
    AddressService addressService;

    /**
     * 收货地址
     * 1. 查询所有收货地址
     * 2. 新增 删除 修改
     * 3. 设置默认地址
     */
    @ApiOperation(value = "新增收获地址", notes = "新增收获地址", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(
            @ApiParam(name = "addressBO", value = "收货地址", required = true)
            @RequestBody AddressBO addressBO
    ) {
        JsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.create(addressBO);
        return JsonResult.ok();
    }


    @ApiOperation(value = "查询收获地址", notes = "查询收获地址", httpMethod = "POST")
    @PostMapping("/list")
    public JsonResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAllAddress(userId);
        return JsonResult.ok(list);
    }

    @ApiOperation(value = "修改收获地址", notes = "修改收获地址", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResult update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JsonResult.errorMsg("");
        }
        JsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != HttpStatus.OK.value()) {
            return checkRes;
        }
        addressService.update(addressBO);
        return JsonResult.ok();

    }

    @ApiOperation(value = "删除收获地址", notes = "删除收获地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResult delete(@RequestParam String userId, @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) && StringUtils.isBlank(addressId)) {
            return JsonResult.errorMsg("");
        }
        addressService.deleteAddressById(addressId);
        return JsonResult.ok();
    }

    @ApiOperation(value = "设置默认收获地址", notes = "设置默认收获地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public JsonResult setDefalut(@RequestParam String userId, @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) && StringUtils.isBlank(addressId)) {
            return JsonResult.errorMsg("");
        }
        addressService.defaultAddress(userId, addressId);
        return JsonResult.ok();
    }


    /**
     * 校验收货地址
     *
     * @param addressBO
     * @return
     */
    private JsonResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JsonResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JsonResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JsonResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JsonResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JsonResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JsonResult.errorMsg("收货地址信息不能为空");
        }

        return JsonResult.ok();
    }
}
