package com.pizza_delivery.api.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    @SuppressWarnings("unused")
    public String email;
    @SuppressWarnings("unused")
    public String telefon;
    public String street;
    public String streetNumber;
    public String zip;
    public String city;
    public String firstname;
    public String lastname;

}