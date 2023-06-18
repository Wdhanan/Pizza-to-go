package com.pizza_to_go.api.dto;

import com.pizza_to_go.model.CartPizza;
import com.pizza_to_go.model.Pizza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PizzaDto implements Serializable {

	private String size = "";
	private List<String> topping = new ArrayList<>();
	public PizzaDto() {

	}

	public PizzaDto(CartPizza pizza) {
		this.size = pizza.size;
		if (pizza.topping1 != null) {
			topping.add(pizza.topping1);
		}
		if (pizza.topping2 != null) {
			topping.add(pizza.topping2);
		}
		if (pizza.topping3 != null) {
			topping.add(pizza.topping3);
		}
		if (pizza.topping4 != null) {
			topping.add(pizza.topping4);
		}
		if (pizza.topping5 != null) {
			topping.add(pizza.topping5);
		}
	}
	public PizzaDto(Pizza pizza) {
		this.size = pizza.getSize();
		if (pizza.getTopping1() != null) {
			topping.add(pizza.getTopping1());
		}
		if (pizza.getTopping2() != null) {
			topping.add(pizza.getTopping2());
		}
		if (pizza.getTopping3() != null) {
			topping.add(pizza.getTopping3());
		}
		if (pizza.getTopping4() != null) {
			topping.add(pizza.getTopping4());
		}
		if (pizza.getTopping5() != null) {
			topping.add(pizza.getTopping5());
		}
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<String> getTopping() {
		return topping;
	}

	public void setTopping(List<String> topping) {
		this.topping = topping;
	}

	public float getPrice() {
		float price;
		if (size.equals("small")) {
			price = 4f + 0.4f * getTopping().size();
		} else if (size.equals("medium")) {
			price = 5f + 0.5f * getTopping().size();
		} else {
			price = 6f + 0.6f * getTopping().size();
		}
		System.out.println("Size: " + getTopping().size());
		System.out.println(price);
		return price;
	}

}
