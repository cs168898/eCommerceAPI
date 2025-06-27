package com.ecommerce.ecommerce_api.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(UserMessage userMessage) throws Exception{
        Thread.sleep(1000); // simulate a delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(userMessage.getName()) + "!");
    }
}
