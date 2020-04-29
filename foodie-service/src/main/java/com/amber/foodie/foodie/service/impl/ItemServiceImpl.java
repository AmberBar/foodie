package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.common.utils.DesensitizationUtil;
import com.amber.foodie.common.utils.PageResult;
import com.amber.foodie.foodie.service.ItemService;
import com.amber.foodie.mapper.*;
import com.amber.foodie.mapper.customer.ItemsCommentsCustomerMapper;
import com.amber.foodie.mapper.customer.ItemsCustomerMapper;
import com.amber.foodie.pojo.*;
import com.amber.foodie.pojo.enums.CommentLevel;
import com.amber.foodie.pojo.vo.CommentLevelVO;
import com.amber.foodie.pojo.vo.CommentVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemsMapper itemsMapper;
    @Autowired
    ItemsParamMapper itemsParamMapper;
    @Autowired
    ItemsSpecMapper itemsSpecMapper;
    @Autowired
    ItemsImgMapper itemsImgMapper;
    @Autowired
    ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    ItemsCommentsCustomerMapper itemsCommentsCustomerMapper;
    @Autowired
    ItemsCustomerMapper itemsCustomerMapper;

    @Override
    public Items queryItemById(String itemId) {
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", itemId);
        Items item = itemsMapper.selectOneByExample(example);
        return item;
    }

    @Override
    public List<ItemsImg> queryItemsImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queyItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelVO queryCommentCounts(String itemId) {
        Integer goodCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.GOOD.type);
        Integer normolCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.NORMOL.type);
        Integer badCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.BAD.type);
        Integer totalCommentCounts = goodCommentCounts + badCommentCounts + normolCommentCounts;
        CommentLevelVO commentLevelVo = new CommentLevelVO();
        commentLevelVo.setTotalCounts(totalCommentCounts);
        commentLevelVo.setGoodCounts(goodCommentCounts);
        commentLevelVo.setNormalCounts(normolCommentCounts);
        commentLevelVo.setBadCounts(badCommentCounts);
        return commentLevelVo;
    }

    /**
     * @param itemId
     * @param level  1,2,3好评中评差评
     * @return
     */
    Integer queryCommentCountsByLevel(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null) {
            itemsComments.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }

    @Override
    public PageResult queryComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, pageSize);
        List<CommentVO> commentVos = itemsCommentsCustomerMapper.queryItemComments(map);
        // 信息脱敏
        List<CommentVO> collect = commentVos.stream().map(commentVo -> {
            commentVo.setNickname(DesensitizationUtil.commonDisplay(commentVo.getNickname()));
            return commentVo;
        }).collect(Collectors.toList());
        PageResult pageResult = PageResult.converPage(collect, page);
        return pageResult;
    }

    /**
     * @param keywords 　搜索关键字
     * @param sort     　排序
     * @param page     第几页
     * @param pageSize 　每页大小
     * @return
     */
    @Override
    public PageResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        HashMap hashMap = new HashMap();
        hashMap.put("keywords", keywords);
        hashMap.put("sort", sort);
        PageHelper.startPage(page, pageSize);

        List list = itemsCustomerMapper.searchItems(hashMap);
        return PageResult.converPage(list, page);
    }
}
