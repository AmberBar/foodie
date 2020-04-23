package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.ItemService;
import com.amber.foodie.mapper.ItemsImgMapper;
import com.amber.foodie.mapper.ItemsMapper;
import com.amber.foodie.mapper.ItemsParamMapper;
import com.amber.foodie.mapper.ItemsSpecMapper;
import com.amber.foodie.pojo.Items;
import com.amber.foodie.pojo.ItemsImg;
import com.amber.foodie.pojo.ItemsParam;
import com.amber.foodie.pojo.ItemsSpec;
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
}
