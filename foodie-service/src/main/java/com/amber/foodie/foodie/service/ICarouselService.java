package com.amber.foodie.foodie.service;


import com.amber.foodie.pojo.Carousel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICarouselService {

    public List<Carousel> quaryAll(Integer isShow);
}
