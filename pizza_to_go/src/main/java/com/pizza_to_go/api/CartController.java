package com.pizza_to_go.api;

import com.pizza_to_go.dao.CartPizzaDAO;
import com.pizza_to_go.model.CartPizza;
import com.pizza_to_go.pizza_to_go.Services.UserService;
import com.pizza_to_go.api.dto.OrderDTOFront;
import com.pizza_to_go.api.dto.PizzaDto;
import com.pizza_to_go.api.dto.UserDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("cart")
public class CartController {

    @Inject
    CartPizzaDAO cartPizzaDAO;
    WebTarget userManagementUserTarget = ClientBuilder.newClient().target("http://localhost:9082/data/user");
    private String getUsername(String token) {
        try (Response response = userManagementUserTarget.queryParam("token", token).request(MediaType.APPLICATION_JSON).get()) {
            UserDto userDto = response.readEntity(UserDto.class);
            return userDto.username;
        }
    }
    @GET
    @Transactional
    public Response get(@HeaderParam("loginToken") String token) {
        if(!UserService.isUserHasAccess(token)) {
            return Response.status(401).build();
        }
        OrderDTOFront orderDTOFront = new OrderDTOFront();
        for(CartPizza cartPizza: cartPizzaDAO.getPizzas(getUsername(token))) {
            orderDTOFront.pizza.add(new PizzaDto(cartPizza));
        }
        return Response.ok(orderDTOFront).build();
    }
    @POST
    @Transactional
    public Response post(@HeaderParam("loginToken") String token, OrderDTOFront orderDTOFront) {
        if(!UserService.isUserHasAccess(token)) {
            return Response.status(401).build();
        }
        delete(token);
        for(PizzaDto pizzaDto : orderDTOFront.pizza) {
            cartPizzaDAO.insert(pizzaDto, getUsername(token));
        }
        return Response.ok().build();
    }
    @DELETE
    @Transactional
    public Response delete(@HeaderParam("loginToken") String token) {
        if(!UserService.isUserHasAccess(token)) {
            return Response.status(401).build();
        }
        cartPizzaDAO.delete(getUsername(token));
        return Response.ok().build();
    }
}
