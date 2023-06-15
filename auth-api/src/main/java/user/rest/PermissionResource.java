package user.rest;

import core.response.PagedResponseDto;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import user.dto.PermissionDto;
import user.entity.Permission;
import user.service.PermissionService;

import java.util.List;

@Path("/permission")
@Slf4j
public class PermissionResource {

    @Inject
    PermissionService permissionService;

    @POST
    @Path("/permissions")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(PermissionDto dto){
        PermissionDto createdPermission = permissionService.createPermission(dto);
        return Response.ok().entity(createdPermission).build();
    }

    @GET
    @Path("/permissions")
    public Response getPermissions(@QueryParam("code") String code, @DefaultValue("0") @QueryParam("page[index]") int pageIndex,@DefaultValue("10") @QueryParam("page[size]") int pageSize) {
        PagedResponseDto<List<PermissionDto>> response = permissionService.paginate(pageIndex,pageSize, code);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/permissions/{permissionId}")
    public Response getPermission(@PathParam("permissionId") Long permissionId) {
        Permission one = permissionService.findOne(permissionId);
        if (one == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(permissionService.mapToDto(one)).build();
    }

    @PATCH
    @Transactional
    @Path("/permissions/{permissionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePermission(@PathParam("permissionId") Long permissionId, PermissionDto dto) {
        Permission one = permissionService.findOne(permissionId);
        if (one == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        PermissionDto updatedDto = permissionService.updatePermission(permissionId, dto);
        return Response.ok().entity(updatedDto).build();
    }

    @DELETE
    @Transactional
    @Path("/permissions/{permissionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePermission(@PathParam("permissionId") Long permissionId) {
        Permission one = permissionService.findOne(permissionId);
        if (one == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        permissionService.deletePermission(permissionId);
        return Response.noContent().build();
    }



}
