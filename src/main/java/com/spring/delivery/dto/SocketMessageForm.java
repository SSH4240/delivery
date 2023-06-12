package com.spring.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocketMessageForm {
    private boolean state;
    private String message;
    private Long userId;
    private String userEmail;

    public SocketMessageForm(boolean state, String message) {
        this.state = state;
        this.message = message;
    }
    public SocketMessageForm(boolean b) {
    }

    public SocketMessageForm(boolean b, String message, Long userId) {
    }
}