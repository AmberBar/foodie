package com.amber.foodie.foodie.service;



import com.amber.foodie.pojo.Carousel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICarouselService  {
    /**
     * 查询所有轮播图
     * @param isShow
     * @return
     */
    public List<Carousel> quaryAll(Integer isShow);
}
