package com.pizza_to_go.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cartpizza")
@NamedQuery(name = "CartPizza.findByUsername", query = "SELECT p FROM CartPizza p WHERE p.username = :username")
@SuppressWarnings("unused")
public class CartPizza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_id", nullable = false)
    public Integer pizzaId;

    @Column(length = 20, nullable = false)
    public String username;

    @Column(length = 20, nullable = false)
    public String size;

    @Column(length = 50)
    public String topping1;
    @Column(length = 50)
    public String topping2;
    @Column(length = 50)
    public String topping3;
    @Column(length = 50)
    public String topping4;
    @Column(length = 50)
    public String topping5;
}
