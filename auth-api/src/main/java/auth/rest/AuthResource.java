package auth.rest;

import auth.annotation.Permissions;
import auth.annotation.RequiresToken;
import auth.dto.LoginRequestDto;
import auth.dto.LoginResponseDto;
import auth.exception.AuthException;
import auth.service.AuthService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;

@Path("/auth")
@Slf4j
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @PermitAll
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDto request){
        try {
            LoginResponseDto loginResponseDto = this.authService.login(request.getUsername(), request.getPassword());
            return Response.ok().entity(loginResponseDto).build();
        } catch (BadRequestException | AuthException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/who-am-i")
    @RequiresToken()
    @Produces(MediaType.APPLICATION_JSON)
    public Response me(@Context SecurityContext securityContext) {
        return Response.status(200).entity(securityContext.getUserPrincipal()).build();
    }

    @GET
    @Path("/check")
    @Permissions(value = {"USER_R", "USER_C"}, inclusive = true)
    @RequiresToken
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@Context SecurityContext securityContext) {
        return Response.status(200).entity(securityContext.getUserPrincipal()).build();
    }



}
