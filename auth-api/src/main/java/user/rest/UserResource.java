package user.rest;

import core.response.PagedResponseDto;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import user.dto.UserDto;
import user.entity.User;
import user.service.UserGenerationService;
import user.service.UserService;

import java.net.URI;
import java.util.List;

@Path("/user")
@Slf4j
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserGenerationService userGenerationService;

    @Context
    UriInfo uriInfo;

    @POST
    @Path("/users")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserDto dto){
        UserDto createdUser = userService.createUser(dto);
        return Response.ok().entity(createdUser).build();
    }


    @GET
    @Path("/users")
    public Response getUsers(String name) {
        PagedResponseDto<List<UserDto>> response = null;
        try {
            response = userService.paginate(0,5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().entity(response).build();
    }


    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFiveUsers(){
        URI uri = uriInfo.getBaseUriBuilder().path(UserResource.class).path(UserResource.class,"createFiveUsers").build();
        userGenerationService.createFiveUsers();
        return Response.created(uri).entity(uri).build();
    }

    @GET
    @Path("/criteria/1")
    public Response criteriaQuery1() {
        List<UserDto> response = userService.criteriaQuery1();
        return Response.ok().entity(response).build();
    }


}
