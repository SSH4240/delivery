package com.spring.delivery.service;

import com.spring.delivery.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
//    @InjectMocks
//    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;


}