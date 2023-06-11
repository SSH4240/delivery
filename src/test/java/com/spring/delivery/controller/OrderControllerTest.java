package com.spring.delivery.controller;

import com.spring.delivery.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderControllerTest {
    @Autowired
    OrderService orderService;

    @Test
    void 주문_장바구니(){
        //given

    }

}