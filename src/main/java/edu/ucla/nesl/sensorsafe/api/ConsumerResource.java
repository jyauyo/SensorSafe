package edu.ucla.nesl.sensorsafe.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.SecurityContext;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import edu.ucla.nesl.sensorsafe.auth.Roles;
import edu.ucla.nesl.sensorsafe.db.DatabaseConnector;
import edu.ucla.nesl.sensorsafe.db.UserDatabaseDriver;
import edu.ucla.nesl.sensorsafe.init.SensorSafeResourceConfig;
import edu.ucla.nesl.sensorsafe.model.ResponseMsg;
import edu.ucla.nesl.sensorsafe.model.User;
import edu.ucla.nesl.sensorsafe.tools.WebExceptionBuilder;

@Path("consumers")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "consumers", description = "Operations about consumers.")
public class ConsumerResource {
	
	@Context
	private SecurityContext securityContext;
	
	@RolesAllowed(Roles.OWNER)
	@GET
    @ApiOperation(value = "List currently registered consumers on this server.", notes = "TBD")
    @ApiResponses(value = {
    		@ApiResponse(code = 500, message = "Internal Server Error")
    })
	public List<User> getConsumerList() {
		String ownerName = securityContext.getUserPrincipal().getName();
		UserDatabaseDriver db = null;
		List<User> users = null;
		try {
			db = DatabaseConnector.getUserDatabase();
			users = db.getConsumers(ownerName);
		} catch (ClassNotFoundException | IOException | NamingException | SQLException e) {
			throw WebExceptionBuilder.buildInternalServerError(e);
		} catch (IllegalArgumentException e) {
			throw WebExceptionBuilder.buildBadRequest(e);
		} finally {
			if (db != null) { 
				try {
					db.close();
				} catch (SQLException e) {
					throw WebExceptionBuilder.buildInternalServerError(e);
				}
			}
		}
		return users;
	}

	@RolesAllowed(Roles.OWNER)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add a new consumer.", notes = "TBD")
    @ApiResponses(value = {
    		@ApiResponse(code = 500, message = "Internal Server Error")
    })
	public ResponseMsg addNewConsumer(
			@ApiParam(name = "new_consumer"
						, value = "Only username and password required.  If email is provided, "
								+ "the server will send an email with "
								+ "Apikey, OAuth Consumer Key, and Secret."
						, required = true)
			User newConsumer) {
		String ownerName = securityContext.getUserPrincipal().getName();
		UserDatabaseDriver db = null;
		try {
			db = DatabaseConnector.getUserDatabase();
			newConsumer = db.addConsumer(newConsumer, ownerName);
			SensorSafeResourceConfig.oauthProvider.registerConsumer(ownerName, newConsumer.oauthConsumerKey, newConsumer.oauthConsumerSecret, new MultivaluedHashMap<String, String>());
		} catch (ClassNotFoundException | IOException | NamingException | SQLException e) {
			throw WebExceptionBuilder.buildInternalServerError(e);
		} catch (IllegalArgumentException e) {
			throw WebExceptionBuilder.buildBadRequest(e);
		} finally {
			if (db != null) { 
				try {
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResponseMsg("Successfully added a consumer.");
	}
	
	@RolesAllowed(Roles.OWNER)
	@DELETE
    @ApiOperation(value = "Delete a consumer.", notes = "TBD")
    @ApiResponses(value = {
    		@ApiResponse(code = 500, message = "Internal Server Error")
    })
	public ResponseMsg deleteConsumer(
			@ApiParam(name = "username", value = "Enter a username you want to delete.", required = true)
			@QueryParam("username") String consumerName) {
		
		String ownerName = securityContext.getUserPrincipal().getName();
		UserDatabaseDriver db = null;
		try {
			db = DatabaseConnector.getUserDatabase();
			User deletedConsumer = db.deleteConsumer(consumerName, ownerName);
			if (deletedConsumer != null && deletedConsumer.oauthAccessKey != null) {
				SensorSafeResourceConfig.oauthProvider.revokeAccessToken(deletedConsumer.oauthAccessKey, deletedConsumer.username);
			}
		} catch (ClassNotFoundException | IOException | NamingException | SQLException e) {
			throw WebExceptionBuilder.buildInternalServerError(e);
		} catch (IllegalArgumentException e) {
			throw WebExceptionBuilder.buildBadRequest(e);
		} finally {
			if (db != null) { 
				try {
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResponseMsg("Successfully deleted a consumer.");
	}
}