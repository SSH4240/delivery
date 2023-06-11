package com.spring.delivery.handler;

import com.spring.delivery.domain.Order;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.dto.SocketMessageForm;
import com.spring.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderMessageHandler {
    private final OrderService orderService;

    @MessageMapping("/order/create")
    @SendTo("/topic/orders")
    public SocketMessageForm sendOrder(OrderDTO orderDTO){
        orderService.create(orderDTO);

        return new SocketMessageForm(true, "주문 접수가 완료되었습니다.");
    }

    @MessageMapping("/order/cancel")
    @SendTo("/topic/orders")
    public ResponseEntity<String> cancelOrder(Long orderId){
        orderService.cancel(orderId);
        return new ResponseEntity<>("주문 취소가 완료되었습니다.", HttpStatus.OK);
    }

//    @MessageMapping("/order/list/user")
//    @SendTo("/topic/orders")
//    public ResponseEntity<List<Order>> orderListByUser(Long userId){
//        List<Order> orders = orderService.findAllOrdersByUserId(userId);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }
//
//    @MessageMapping("/order/list/manager")
//    @SendTo("/topic/orders")
//    public ResponseEntity<List<Order>> findAllOrders(){
//        List<Order> orders = orderService.findAllOrders();
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }

    @MessageMapping("/order/accept")
    @SendTo("/topic/orders")
    public ResponseEntity<String> acceptOrder(Long orderId){
        orderService.acceptOrder(orderId);
        return new ResponseEntity<>("주문을 수락하였습니다.", HttpStatus.OK);
    }

    @MessageMapping("/order/complete")
    @SendTo("/topic/orders")
    public ResponseEntity<String> completeDelivery(Long orderId){
        orderService.setOrderDelivered(orderId);
        return new ResponseEntity<>("배달이 완료되었습니다.", HttpStatus.OK);
    }
}
