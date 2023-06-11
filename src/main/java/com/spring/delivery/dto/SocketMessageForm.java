package com.spring.delivery.dto;

import lombok.Data;

@Data
public class SocketMessageForm {
    private boolean state;
    private String message;

    public SocketMessageForm(boolean state, String message) {
        this.state = state;
        this.message = message;
    }
}