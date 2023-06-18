package com.pizza_to_go.pizza_to_go.Services;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.HttpHeaders;
import java.util.List;

public class UserService {

    private static WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:9082/data/access");

    public static boolean isUserHasAccess(String token) {

        if ( token == null || token.equals(""))
            return false;

        Response response = webTarget.queryParam("token", token).request(MediaType.APPLICATION_JSON).get();
        try {
            return response.readEntity(boolean.class);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
