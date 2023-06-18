package com.pizza_to_go.dao;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.pizza_to_go.model.Orders;

@Singleton
@SuppressWarnings("unused")
public class OrderDAO {

	@PersistenceContext(name = "jpa-unit")
	EntityManager em;

	public Optional<Orders> findOrder(int orderId) {
		Query query = em.createNamedQuery("Orders.findByOrderId", Orders.class);
		query.setParameter("orderId", orderId);
		Orders order = (Orders) query.getSingleResult();

		if (order != null) {
			return Optional.of(order);
		} else {
			return Optional.empty();
		}
	}

	public int createOrder(String username) {
		try {
			Orders order = new Orders();
			order.setUsername(username);
			order.setDateOrder(Date.from(Instant.now()));

			em.persist(order);
			em.flush();
			em.refresh(order);
			return order.getOrderId();
		} catch (Throwable thr) {
			thr.printStackTrace();
			throw new RuntimeException("ERROR createOrder");
		}
	}

	public void updatePriceOrder(int orderId, float price) {
		try {
			Orders order = findOrder(orderId).get();
			float old_price = order.getPrice();
			order.setPrice(price + old_price);
			em.merge(order);
		} catch (Throwable thr) {
			throw new RuntimeException("ERROR updatePriceOrder");
		}
	}
	
	public void updateDateOrder(int orderId, Date dateOrder) {
		try {
			Orders order = findOrder(orderId).get();
			order.setDateOrder(dateOrder);
			em.merge(order);
		} catch (Throwable thr) {
			throw new RuntimeException("ERROR updatePriceOrder");
		}
	}

}
