package com.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteItemRequest {
    private UUID orderId;
    private UUID itemId;
}