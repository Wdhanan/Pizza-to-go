package com.pizza_to_go.api;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pizza_to_go.api.dto.OrderDTOFront;
import com.pizza_to_go.api.dto.OrderDTOOut;
import com.pizza_to_go.api.dto.PizzaDto;
import com.pizza_to_go.api.dto.UserDto;
import com.pizza_to_go.dao.PizzaDAO;
import com.pizza_to_go.pizza_to_go.Services.UserService;
import com.pizza_to_go.dao.OrderDAO;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {
    WebTarget pizzaDeliveryTarget = ClientBuilder.newClient().target("http://localhost:9080/api/delivery");
    WebTarget userManagementUserTarget = ClientBuilder.newClient().target("http://localhost:9082/data/user");
    @Inject
    OrderDAO orderDAO;

    @Inject
    PizzaDAO pizzaDAO;
    
    private String getUsername(String token) {
        try (Response response = userManagementUserTarget.queryParam("token", token).request(MediaType.APPLICATION_JSON).get()) {
            UserDto userDto = response.readEntity(UserDto.class);
            System.out.println("Username: " + userDto.username);
            return userDto.username;
        }
    }

    @POST
    @Transactional
    public Response sendOrder(@HeaderParam("loginToken") String token, OrderDTOFront order) {
        if(!UserService.isUserHasAccess(token)) {
            return Response.status(401).build();
        }
        String username = getUsername(token);

        int orderId = orderDAO.createOrder(username);
        List<PizzaDto> pizzas = order.pizza;
        float price = 0.0f;
        for (PizzaDto pizza : pizzas) {
            pizzaDAO.createPizza(orderId, pizza.getSize(), pizza.getPrice(), pizza.getTopping());
            price += pizza.getPrice();
        }
        orderDAO.updatePriceOrder(orderId, price);
        try (Response response = pizzaDeliveryTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(new OrderDTOOut(order, orderId, username), MediaType.APPLICATION_JSON))) {
            return Response.ok(price).build();
        }
    }
}
