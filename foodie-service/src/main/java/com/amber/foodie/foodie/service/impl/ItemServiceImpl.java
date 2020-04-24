package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.ItemService;
import com.amber.foodie.mapper.*;
import com.amber.foodie.pojo.*;
import com.amber.foodie.pojo.enums.CommentLevel;
import com.amber.foodie.pojo.vo.CommentLevelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
    public CommentLevelVo queryCommentCounts(String itemId) {
        Integer goodCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.GOOD.type);
        Integer normolCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.NORMOL.type);
        Integer badCommentCounts = queryCommentCountsByLevel(itemId, CommentLevel.BAD.type);
        Integer totalCommentCounts = goodCommentCounts + badCommentCounts + normolCommentCounts;
        CommentLevelVo commentLevelVo = new CommentLevelVo();
        commentLevelVo.setTotalCounts(totalCommentCounts);
        commentLevelVo.setGoodCounts(goodCommentCounts);
        commentLevelVo.setNormalCounts(normolCommentCounts);
        commentLevelVo.setBadCounts(badCommentCounts);
        return commentLevelVo;
    }

    /**
     *
     * @param itemId
     * @param level 1,2,3好评中评差评
     * @return
     */
    Integer queryCommentCountsByLevel(String itemId, Integer level) {
        ItemsComments  itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null) {
            itemsComments.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }
}
