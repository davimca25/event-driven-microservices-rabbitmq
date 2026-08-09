package com.microservices.processing;

import com.microservices.processing.dto.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @RabbitListener(queues = "order.processing.queue")
    public void receiveOrder(@Payload OrderDTO orderDTO) {
        System.out.println("Order received: " + orderDTO.getName());
    }
}
