package com.user_management.api;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.thrift.annotation.Nullable;

import com.user_management.api.access.AccessManager;
import com.user_management.api.dto.Token;
import com.user_management.api.dto.UserDTOIn;
import com.user_management.api.dto.UserDTOOut;
import com.user_management.dao.UserDAO;
import com.user_management.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

	@Inject
	private UserDAO userDAO;

	@Inject
	private AccessManager accessManager;

	@GET
	public UserDTOOut getUser(@QueryParam("username") String username, @QueryParam("token") String token) {
		if (token != null) {
			username = accessManager.getUsername(UUID.fromString(token));
		}
		Optional<User> optUser = userDAO.findUser(username);
		if (optUser.isPresent()) {
			UserDTOOut userDto = new UserDTOOut(optUser.get());
			return userDto;
		} else
			throw new RuntimeException("ERROR: User not found");
	}

	@DELETE
	@Transactional
	public boolean deleteUser(@QueryParam("token") UUID uuid) {
		if (accessManager.hasAccess(uuid) == false) {
			throw new RuntimeException("ERROR: Access not granted");
		}

		String username = accessManager.getUsername(uuid);

		if (userDAO.deleteUser(username)) {
			accessManager.deregister(username);
			return true;
		} else {
			return false;
		}
	}

	@POST
	@Transactional
	public Token register(UserDTOIn user) {

		userDAO.createUser(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(),
				user.getEmail(), user.getTelefon(), user.getStreet(), user.getStreetNumber(), user.getZip(),
				user.getCity());

		UUID uuid = accessManager.register(user.getUsername(), user.getPassword());

		return new Token(uuid);
	}

	@GET
	@Path("/isAvailable")
	public boolean usernameAvailable(@QueryParam("username") String username) {
		Optional<User> optUser = userDAO.findUser(username);
		if (optUser.isPresent()) {
			return false;
		} else {
			System.out.println("Username " + username+ " is available");
			return true;
		}
	}
}
