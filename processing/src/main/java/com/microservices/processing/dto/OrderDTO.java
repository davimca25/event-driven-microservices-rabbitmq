package com.microservices.processing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String name;
    private List<OrderItemDTO> items;
}
