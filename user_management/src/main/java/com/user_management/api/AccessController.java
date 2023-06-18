package com.user_management.api;
import java.util.UUID;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.user_management.api.access.AccessManager;
import com.user_management.api.dto.LoginDto;
import com.user_management.api.dto.UserDTOOut;
import com.user_management.dao.UserDAO;
import com.user_management.model.User;
import com.user_management.api.dto.Token;

@Path("/access")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccessController {

	@Inject
	private UserDAO userDAO;
	@Inject
	private AccessManager accessManager;

	@POST
	public Token login(LoginDto loginDto) {
		try {
			System.out.println(loginDto);

			UUID uuid = accessManager.register(loginDto.getUsername(), loginDto.getPassword());

			System.out.println("Login : " + loginDto.getUsername());
			System.out.println("  -> " + uuid);

			return new Token(uuid);
		} catch (Throwable thr) {
			throw new RuntimeException("ERROR: login: " + thr.getMessage());
		}
	}

	@DELETE
	public Response logout(@QueryParam("token") String token) {

		System.out.println("Token: " + token);
		String username = accessManager.getUsername(UUID.fromString(token));
		System.out.println("Logout: " + username);

		return accessManager.deregister(username) ? Response.ok().build() : Response.status(404).build();
	}

	@GET
	public boolean hasAccess(@QueryParam("token") String token){
		return accessManager.hasAccess(UUID.fromString(token));
	}
}
