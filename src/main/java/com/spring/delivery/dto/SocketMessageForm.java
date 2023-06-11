package com.spring.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocketMessageForm {
    private boolean state;
    private String message;

    public SocketMessageForm(boolean state, String message) {
        this.state = state;
        this.message = message;
    }

    public SocketMessageForm() {
        
    }

    public SocketMessageForm(boolean b) {
    }
}