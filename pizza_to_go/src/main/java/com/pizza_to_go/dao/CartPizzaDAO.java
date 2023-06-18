package com.pizza_to_go.dao;

import com.pizza_to_go.api.dto.PizzaDto;
import com.pizza_to_go.model.CartPizza;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class CartPizzaDAO {
    @PersistenceContext(name = "jpa-unit")
    EntityManager em;

    public void insert(PizzaDto pizzaDto, String username) {
        CartPizza cartPizza = new CartPizza();
        switch(pizzaDto.getTopping().size()) {
            case 5:
            cartPizza.topping5 = pizzaDto.getTopping().get(4);
            case 4:
            cartPizza.topping4 = pizzaDto.getTopping().get(3);
            case 3:
            cartPizza.topping3 = pizzaDto.getTopping().get(2);
            case 2:
            cartPizza.topping2 = pizzaDto.getTopping().get(1);
            case 1:
            cartPizza.topping1 = pizzaDto.getTopping().get(0);
            default:
            break;
        }
        cartPizza.size = pizzaDto.getSize();
        cartPizza.username = username;
        em.persist(cartPizza);
        em.flush();
        em.refresh(cartPizza);
    }
    @SuppressWarnings("unchecked")
    public List<CartPizza> getPizzas(String username) {
        Query query = em.createNamedQuery("CartPizza.findByUsername", CartPizza.class);
        query.setParameter("username", username);
        return (List<CartPizza>) query.getResultList();
    }

    public void delete(String username) {
        for (CartPizza cartPizza : getPizzas(username)) {
            em.remove(cartPizza);
        }
    }
}
