package com.pizza_to_go.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pizza")
@NamedQuery(name = "Pizza.findByOrderId", query = "SELECT p FROM Pizza p WHERE p.orderId = :orderId")
@SuppressWarnings("unused")
public class Pizza implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pizza_id", nullable = false)
	private Integer pizzaId;

	@Column(name = "order_id", nullable = false)
	private Integer orderId;

	@Column(length = 20, nullable = false)
	private String size;

	@Column(length = 50)
	private String topping1;

	@Column(length = 50)
	private String topping2;

	@Column(length = 50)
	private String topping3;

	@Column(length = 50)
	private String topping4;

	@Column(length = 50)
	private String topping5;

	@Column(nullable = false)
	private float price;

	public Pizza() {
	}

	public Pizza(Integer orderId, String size, String topping1, String topping2, String topping3, String topping4,
			String topping5, Float price) {
		this.orderId = orderId;
		this.size = size;
		this.topping1 = topping1;
		this.topping2 = topping2;
		this.topping3 = topping3;
		this.topping4 = topping4;
		this.topping5 = topping5;
		this.price = price;
	}

	public Integer getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(Integer pizzaId) {
		this.pizzaId = pizzaId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTopping1() {
		return topping1;
	}

	public void setTopping1(String topping1) {
		this.topping1 = topping1;
	}

	public String getTopping2() {
		return topping2;
	}

	public void setTopping2(String topping2) {
		this.topping2 = topping2;
	}

	public String getTopping3() {
		return topping3;
	}

	public void setTopping3(String topping3) {
		this.topping3 = topping3;
	}

	public String getTopping4() {
		return topping4;
	}

	public void setTopping4(String topping4) {
		this.topping4 = topping4;
	}

	public String getTopping5() {
		return topping5;
	}

	public void setTopping5(String topping5) {
		this.topping5 = topping5;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
