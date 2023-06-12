package com.spring.delivery.dto;

import com.spring.delivery.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsDTO {
    private Long id;
    private Long menuId;
    private String menuName;
    private int menuPrice;
    private int quantity;
}
