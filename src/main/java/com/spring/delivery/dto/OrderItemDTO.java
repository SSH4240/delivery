package com.spring.delivery.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long storeId;
    private Long menuId;
    private int quantity;
}
