package com.user_management.api.dto;

import java.io.Serializable;

import com.user_management.model.User;

@SuppressWarnings("serial")
public class UserDTOOut implements Serializable {
	private String email;
	private String telefon;
	private String street;
	private String streetNumber;
	private String zip;
	private String city;
	private String firstname;
	private String username;


	public String getUsername() {
		return username;
	}

	private String lastname;

	public UserDTOOut() {

	}

	public UserDTOOut(User user) {
		this.email = user.getEmail();
		this.telefon = user.getTelefon();
		this.street = user.getStreet();
		this.streetNumber = user.getStreetNumber();
		this.zip = user.getZip();
		this.city = user.getCity();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.username = user.getUsername();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
