package com.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateItemRequest {

    private int quantity;
    private BigDecimal price;
}
