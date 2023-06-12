package com.spring.delivery.service;

import com.spring.delivery.domain.*;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.dto.OrderItemDTO;
import com.spring.delivery.dto.SocketMessageForm;
import com.spring.delivery.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class OrderServiceTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    StoreService storeService;

    @Test
    void 주문_장바구니_테스트(){
        //given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCurrentHour(9);
        orderDTO.setTotalPrice(9000);
        orderDTO.setStoreId(1L);
        //when
        SocketMessageForm socketMessageForm = orderService.create(orderDTO);

        //then
        log.info("실행 결과 : {}", socketMessageForm.getMessage());
    }

    @Test
    void 전체_주문내역(){
        //given
        Menu menu = new Menu();
        menu.setName("햄버거");
        menu.setPrice(1999);
        menu.setId(5L);
        List<Order> orderList = new ArrayList<>();
//        Order order = new Order();
//        order.setOrderTime(LocalDateTime.now());
//        order.setStatus(OrderStatus.ORDER);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCurrentHour(9);
        orderDTO.setTotalPrice(9000);
        orderDTO.setStoreId(1L);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setMenuId(5L);
        orderItemDTO.setStoreId(1L);
        orderItemDTO.setQuantity(5);
        List<OrderItemDTO> dtos = new ArrayList<>();
        dtos.add(orderItemDTO);
        orderDTO.setOrderItem(dtos);
        orderService.create(orderDTO);
        //when
        orderList = orderService.findAllOrders();
        //then
        log.info("주문내역 = {}", orderList);
    }
}