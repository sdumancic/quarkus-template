package infrastructure.rest;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import service.CompanyDto;
import service.CompanyService;
import util.PageableSortableRequest;

@RequestScoped
@Path("/company")
@Slf4j
public class CompanyResource {

    @Inject
    CompanyService companyService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanies(@QueryParam("pageNo") int pageNo,
                                 @QueryParam("pageSize") int pageSize,
                                 @QueryParam("sort") String sort,
                                 @QueryParam("name") String name,
                                 @QueryParam("city") String city,
                                 @QueryParam("country") String country
    ) {
        PageableSortableRequest request = new PageableSortableRequest(pageNo, pageSize, sort);
        return Response.ok().entity(companyService.listAll(request, name, city, country)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CompanyDto dto) {
        CompanyDto createdCompany = companyService.createCompany(dto);
        return Response.ok().entity(createdCompany).build();
    }
}
