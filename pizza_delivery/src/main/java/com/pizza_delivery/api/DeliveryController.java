package com.pizza_delivery.api;

import com.pizza_delivery.api.dto.OrderDto;
import com.pizza_delivery.api.dto.UserDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.*;

@Path("/delivery")
@Consumes(MediaType.APPLICATION_JSON)

public class DeliveryController {
    WebTarget userManagementWebTarget = ClientBuilder.newClient().target("http://localhost:9082/data/user");

    @POST
    public void deliver(OrderDto order ){
        try (FileWriter orderLogWriter = new FileWriter("order.log", true)) {
            orderLogWriter.write("[" + java.time.LocalDateTime.now() + "] " + order.toString() +"\n");
            Thread deliverThread = new Thread(() -> {
                try (FileWriter deliveryLogWriter = new FileWriter("delivery.log", true)) {
                    sleep(10000);
                    UserDto user = userManagementWebTarget.queryParam("username", order.username).request(MediaType.APPLICATION_JSON).get().readEntity(UserDto.class);

                    deliveryLogWriter.write("[" + java.time.LocalDateTime.now() + "] "
                            + order.order + " was sent to "
                            + user.firstname + " " + user.lastname + ", "
                            + user.street + " " + user.streetNumber + " " + user.zip + " " + user.city + "\n"
                    );

                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            deliverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}