package com.spring.delivery.controller;

import com.spring.delivery.domain.Store;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.dto.SocketMessageForm;
import com.spring.delivery.repository.StoreRepository;
import com.spring.delivery.service.OrderService;
import com.spring.delivery.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(OrderControllerTest.class)
@SpringBootTest
@Slf4j
class OrderControllerTest {
//    @Autowired
//    MockMvc mockMvc;

    @Autowired
    OrderService orderService;
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;

    @Test
    void 주문_장바구니_시간_외_주문(){
        //given
        Store store = new Store();
        store.setId(555L);
        store.setOpenTime(9);
        store.setClosedTime(10);
        storeRepository.save(store);



        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(543L);
        orderDTO.setOrderTime(LocalDateTime.of(1999, 5, 29, 8, 0, 0));
        orderDTO.setStoreId(555L);
        //when
        SocketMessageForm socketMessageForm = orderService.create(orderDTO);

        //then
        log.info("시간 이외의 주문 결과 : {}", socketMessageForm.getMessage());
    }
}