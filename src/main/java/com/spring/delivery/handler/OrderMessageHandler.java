package com.spring.delivery.handler;

import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderMessageHandler {
    private final OrderService orderService;

    @MessageMapping("/order")
    @SendTo("/topic/orders")
    public ResponseEntity<String> sendOrder(OrderDTO orderDTO){
        orderService.create(orderDTO);

        return new ResponseEntity<>("주문 접수가 완료되었습니다.", HttpStatus.OK);
    }

    @MessageMapping("/orderList")
    @SendTo("/topic/orders")
    public ResponseEntity<String> orderList(OrderDTO orderDTO){
        orderService.create(orderDTO);

        return new ResponseEntity<>("주문 접수가 완료되었습니다.", HttpStatus.OK);
    }

    @MessageMapping("/ordder")
    @SendTo("/topic/orders")
    public ResponseEntity<String> sendffOrder(OrderDTO orderDTO){
        orderService.create(orderDTO);

        return new ResponseEntity<>("주문 접수가 완료되었습니다.", HttpStatus.OK);
    }
}
