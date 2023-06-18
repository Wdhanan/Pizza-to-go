package com.pizza_to_go.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTOFront implements Serializable {
	public List<PizzaDto> pizza;
	public OrderDTOFront() {
		pizza = new ArrayList<>();
	}
}
