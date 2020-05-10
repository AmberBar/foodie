package com.amber.foodie.config;

import com.amber.foodie.foodie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderJob {

    @Autowired
    OrderService orderService;

    /**
     * TODO 换成rabbirmq
     */
    @Scheduled(cron = "0/3 * * * * ? ")
    public void closeOrder() {
        log.info("==========执行定时任务================");
        orderService.closeOrder();
    }
}
