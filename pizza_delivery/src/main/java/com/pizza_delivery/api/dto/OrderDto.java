package com.pizza_delivery.api.dto;


import java.io.Serializable;

public class OrderDto implements Serializable {
    public String username;
    public int order = 0;
    public PizzaDto[] pizza;

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(username + " ordered: ");
        for (PizzaDto p : pizza) {
            ret.append(p.toString()).append(", ");
        }
        return ret.substring(0, ret.length() - 2);
    }
}
