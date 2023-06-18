package com.pizza_to_go.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.pizza_to_go.model.Pizza;

@Singleton
@SuppressWarnings("unused")
public class PizzaDAO {

	@PersistenceContext(name = "jpa-unit")
    EntityManager em;
	
	public void createPizza(int orderId, String size, Float price, List<String> topping) {
		Pizza pizza = new Pizza();
		try {
			pizza.setOrderId(orderId);
			pizza.setSize(size);
			pizza.setPrice(price);
			switch(topping.size()) {
   	         case 5:
    	        pizza.setTopping5(topping.get(4));
     	       	case 4:
        		pizza.setTopping4(topping.get(3));
            	case 3:
            	pizza.setTopping3(topping.get(2));
            	case 2:
            	pizza.setTopping2(topping.get(1));
            	case 1:
            	pizza.setTopping1(topping.get(0));
            	default:
            	break;
        	}
			em.persist(pizza);
			em.flush();
			em.refresh(pizza);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pizza> getPizzas(int orderId) {
		Query query = em.createNamedQuery("Pizza.findByOrderId", Pizza.class);
		query.setParameter("orderId", orderId);

		return (List<Pizza>) query.getResultList();
	}
	
}
