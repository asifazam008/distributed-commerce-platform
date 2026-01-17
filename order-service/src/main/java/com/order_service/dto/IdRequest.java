package com.order_service.dto;

import java.util.UUID;

public class IdRequest {
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
