package user.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import user.dto.PagedResponseDto;
import user.dto.UserDto;
import user.service.UserService;

import java.util.List;

@Path("/user")
@Slf4j
public class UserResource {

    @Inject
    UserService userService;


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
        PagedResponseDto<List<UserDto>> response = userService.paginate(0,5);
        return Response.ok().entity(response).build();
    }



}
