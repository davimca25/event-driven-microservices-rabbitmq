package com.microservices.order.controller;

import com.microservices.order.dto.OrderDTO;
import com.microservices.order.dto.OrderItemDTO;
import com.microservices.order.model.Order;
import com.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final RabbitTemplate rabbitTemplate;
    private final OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(savedOrder.getId());
        orderDTO.setName(savedOrder.getName());

        List<OrderItemDTO> itemDTOs = savedOrder.getItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            return itemDTO;
        }).toList();

        rabbitTemplate.convertAndSend("order.exchange", "order.created", orderDTO);
        System.out.println("Order created: " + order.getName());
        return "Order created: " + order.getName();
    }

    @GetMapping
    public List<Order> orderList() {
        return orderService.listOrders();
    }

}
