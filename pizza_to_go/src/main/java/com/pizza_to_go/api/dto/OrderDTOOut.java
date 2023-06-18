package com.pizza_to_go.api.dto;

import java.io.Serializable;
import java.util.List;

import com.pizza_to_go.model.Orders;
@SuppressWarnings("unused")
public class OrderDTOOut implements Serializable {

	private int order;
	private String username;
	private List<PizzaDto> pizza;

	public OrderDTOOut(Orders order) {
		this.order = order.getOrderId();
		this.username = order.getUsername();
	}

	public OrderDTOOut(OrderDTOFront orderdto, int order, String username) {
		this.order = order;
		this.username = username;
		pizza = orderdto.pizza;
	}

	public int getOrder_id() {
		return order;
	}

	public void setOrder_id(int order_id) {
		this.order = order_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<PizzaDto> getPizza() {
		return pizza;
	}

	public void setPizza(List<PizzaDto> pizza) {
		this.pizza = pizza;
	}

}
