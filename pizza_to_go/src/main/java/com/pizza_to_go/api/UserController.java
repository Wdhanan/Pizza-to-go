package com.pizza_to_go.api;
import com.pizza_to_go.api.dto.UserDto;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    WebTarget userManagementUserTarget = ClientBuilder.newClient().target("http://localhost:9082/data/user");
    WebTarget userManagementUserTargetForUserNameCheck = ClientBuilder.newClient().target("http://localhost:9082/data/user/isAvailable");

    @GET
    @Path("/isAvailable")
    public boolean usernameAvailable(@QueryParam("username") String username) {
        try (Response response = userManagementUserTargetForUserNameCheck.queryParam("username", username).request(MediaType.APPLICATION_JSON).get()) {
            return response.readEntity(boolean.class);
        }
    }
    @GET
    public UserDto getUser(@QueryParam("token") String token) {
        try (Response response = userManagementUserTarget.queryParam("token", token).request(MediaType.APPLICATION_JSON).get()) {
            return response.readEntity(UserDto.class);
        }
    }

    @POST
    @Transactional
    public String register(String user) {
        try (Response response = userManagementUserTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(user, MediaType.APPLICATION_JSON))) {
            return response.readEntity(String.class);
        }
    }

    @DELETE
    @Transactional
    public boolean deleteUser(@QueryParam("token") String token) {
        try (Response response = userManagementUserTarget.queryParam("token", token).request(MediaType.APPLICATION_JSON).delete()) {
            return response.readEntity(boolean.class);
        }
    }
}
