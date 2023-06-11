package com.spring.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long userId;
    private List<OrderItemDTO> orderItem;
    private int totalPrice;
    private Long storeId;
    private LocalDateTime orderTime;
}