package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.ItemsSpecService;
import com.amber.foodie.mapper.ItemsSpecMapper;
import com.amber.foodie.pojo.ItemsSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsSpecServiceImpl implements ItemsSpecService {
    @Autowired
    ItemsSpecMapper itemsSpecMapper;

    @Override
    public int decreaseItemSpecStock(String itemSpecId, Integer buyCounts) {
        return itemsSpecMapper.decreaseItemSpecStock(itemSpecId, buyCounts);
    }

    @Override
    public ItemsSpec findById(String itemsSpecId) {
        return itemsSpecMapper.selectByPrimaryKey(itemsSpecId);
    }
}
