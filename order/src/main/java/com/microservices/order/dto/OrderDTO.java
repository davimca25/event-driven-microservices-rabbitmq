package com.microservices.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderDTO implements Serializable {
    private Long id;
    private String name;
    private List<OrderItemDTO> items;
}
