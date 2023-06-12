package com.spring.delivery.service;

import com.spring.delivery.domain.Store;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.dto.SocketMessageForm;
import com.spring.delivery.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        orderDTO.setOrderTime(LocalDateTime.of(1999, 5, 29, 8, 0, 0));
        orderDTO.setTotalPrice(9000);
        orderDTO.setStoreId(1L);
        //when
        SocketMessageForm socketMessageForm = orderService.create(orderDTO);

        //then
        log.info("실행 결과 : {}", socketMessageForm.getMessage());
    }
}