package com.spring.delivery.handler;

import com.spring.delivery.dto.SocketMessageForm;
import com.spring.delivery.exception.InvalidOrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class WebSocketExceptionHandler {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketExceptionHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    @MessageExceptionHandler
//    public void handleInvalidOrderException(InvalidOrderException e) {
//        SocketMessageForm messageForm = new SocketMessageForm(false, e.getMessage(), e.getUserId());
//        messagingTemplate.convertAndSend("/topic/orders/" + e.getUserId(), messageForm);
//    }
    @MessageExceptionHandler
    @SendTo("/topic/orders")
    public SocketMessageForm handleInvalidOrderException(InvalidOrderException e) {
        SocketMessageForm messageForm = new SocketMessageForm(false);
        messageForm.setMessage(e.getMessage());
        messageForm.setUserId(e.getUserId());
        return messageForm;
    }

}