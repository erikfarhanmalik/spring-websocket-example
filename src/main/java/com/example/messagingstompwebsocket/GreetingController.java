package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.messages.Greeting;
import com.example.messagingstompwebsocket.messages.HelloMessage;
import com.example.messagingstompwebsocket.messages.SetCellsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    private final ObjectMapper objectMapper;

    public GreetingController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings") // this will send response as message to the broker!
    public Greeting greeting(HelloMessage message) {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/setCells/{id}")
    @SendTo("/topic/setCells/{id}")
    public SetCellsMessage setCells(@DestinationVariable String id, @Payload SetCellsMessage message) {
        return message;
    }
}
