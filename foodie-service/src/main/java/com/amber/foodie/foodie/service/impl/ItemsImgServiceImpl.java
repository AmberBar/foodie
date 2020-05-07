package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.ItemsImgService;
import com.amber.foodie.mapper.ItemsImgMapper;
import com.amber.foodie.pojo.ItemsImg;
import com.amber.foodie.pojo.enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsImgServiceImpl implements ItemsImgService {
    @Autowired
    ItemsImgMapper itemsImgMapper;

    @Override
    public ItemsImg findMainImgByItemId(String itemId) {
        ItemsImg itemsImgSelect = new ItemsImg();
        itemsImgSelect.setItemId(itemId);
        itemsImgSelect.setIsMain(YesOrNo.Yes.type);
        return itemsImgMapper.selectOne(itemsImgSelect);
    }
}
