package com.pizza_delivery.api.dto;

import java.io.Serializable;

public class PizzaDto implements Serializable {
    public String size;
    public String[] topping;

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Pizza: (" + size + " with ");
        for (String s : topping) {
            ret.append(s).append(", ");
        }
        return ret.substring(0, ret.length() - 2) + ")";
    }
}
