package com.spring.delivery.controller;
import com.spring.delivery.domain.Order;
import com.spring.delivery.dto.OrderDTO;
import com.spring.delivery.exception.StoreClosedException;
import com.spring.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO){
        orderService.create(orderDTO);
        return new ResponseEntity<>("주문 접수가 완료되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam Long orderId){
        orderService.cancel(orderId);
        return new ResponseEntity<>("주문 취소가 완료되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> findOrderHistory(@RequestParam(required = false) Long userId){
        List<Order> orders = orderService.findAllOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/list/manager")
    public ResponseEntity<List<Order>> findAllOrders(){
        List<Order> orders = orderService.findAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptOrder(@RequestParam Long orderId){
        orderService.acceptOrder(orderId);
        return new ResponseEntity<>("주문을 수락하였습니다.", HttpStatus.OK);
    }

    @PostMapping("/delivery/complete")
    public ResponseEntity<String> completeDelivery(@RequestParam Long orderId){
        orderService.setOrderDelivered(orderId);
        return new ResponseEntity<>("배달이 완료되었습니다.", HttpStatus.OK);
    }

//    @GetMapping("/runtime")
//    public ResponseEntity<String> getRuntime(){
//        return new ResponseEntity<>("런타임?", HttpStatus.OK);
//    }
}
