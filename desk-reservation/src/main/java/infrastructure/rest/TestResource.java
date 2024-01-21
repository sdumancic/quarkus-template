package infrastructure.rest;


import domain.model.Company;
import io.hypersistence.utils.hibernate.query.SQLExtractor;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import util.FilterParams;
import util.FilterQuery;
import util.PredicateGenerator;

import java.util.List;

@RequestScoped
@Path("/test")
@Slf4j
public class TestResource {

    @Inject
    EntityManager em;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        FilterParams p1 = FilterParams.builder()
                .paramType(String.class)
                .paramName("name")
                .paramValue("a")
                .operator(FilterParams.ParamOperator.Like).build();
        FilterParams p2 = FilterParams.builder()
                .paramType(String.class)
                .paramName("city")
                .paramValue("b")
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p3 = FilterParams.builder()
                .paramType(String.class)
                .paramName("country")
                .paramValue("c")
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p4 = FilterParams.builder()
                .paramType(String.class)
                .paramName("postalCode")
                .paramValue("d")
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterQuery filterQuery = (new FilterQuery(p1).or(p2)).and(new FilterQuery(p3).or(p4)).and(new FilterQuery(p1).or(p4));
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Company.class);
        Root root = query.from(Company.class);
        Predicate predicate = PredicateGenerator.generatePredicate(criteriaBuilder, root, filterQuery);

        Predicate predicate2 = criteriaBuilder.and(
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("name"), "a"),
                        criteriaBuilder.equal(root.get("city"), "b")
                ),
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("country"), "c"),
                        criteriaBuilder.equal(root.get("postalCode"), "d")
                ),
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("name"), "a"),
                        criteriaBuilder.equal(root.get("postalCode"), "d")
                )
        );

        query.where(predicate2);
        List<Company> companyList2 = em.createQuery(query).getResultList();
        query.where(predicate);
        List<Company> companyList = em.createQuery(query).getResultList();

        Query query1 = em.createQuery(query);
        String sql = SQLExtractor.from(query1);

        log.info("""
                        The Criteria API, compiled to this JPQL query: [
                            {}
                        ]
                        generates the following SQL query: [
                            {}
                        ]
                        """,
                query1.unwrap(org.hibernate.query.Query.class).getQueryString(),
                sql
        );

        return Response.ok().entity(sql).build();
    }

}
