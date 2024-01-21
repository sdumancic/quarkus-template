package infrastructure.service;

import domain.model.Company;
import domain.model.Location;
import domain.repository.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import service.CompanyDto;
import service.LocationDto;
import util.FilterParams;
import util.FilterQuery;
import util.PageableResource;
import util.PageableSortableRequest;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CompanyRepositoryImpl extends PageableResource implements CompanyRepository {

    @Override
    public List<Company> fetchAll(PageableSortableRequest request, String name, String city, String country) {

        List<FilterParams> filterParamsList = new ArrayList<>();
        FilterParams p1 = FilterParams.builder()
                .paramType(String.class)
                .paramName("name")
                .paramValue(name.concat("%"))
                .operator(FilterParams.ParamOperator.Like).build();
        FilterParams p2 = FilterParams.builder()
                .paramType(String.class)
                .paramName("city")
                .paramValue(city)
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p3 = FilterParams.builder()
                .paramType(String.class)
                .paramName("country")
                .paramValue(country)
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p4 = FilterParams.builder()
                .paramType(String.class)
                .paramName("postalCode")
                .paramValue("40000")
                .operator(FilterParams.ParamOperator.Eq).build();
        filterParamsList.add(p1);
        filterParamsList.add(p2);
        filterParamsList.add(p3);
        FilterQuery fq1 = new FilterQuery(p1).and(p2).or(new FilterQuery(p3).and(p4));

        int firstResult = request.getFirstResult();
        int pageSize = request.getPageSize();

        CriteriaQuery<Long> countQuery1 = this.getCountQuery(Company.class, filterParamsList);
        CriteriaQuery selectQuery = this.getSelectQuery(Company.class, filterParamsList, request.getSort());
        Long count = getEntityManager().createQuery(countQuery1).getSingleResult();

        List<Company> companyList = getEntityManager().createQuery(selectQuery)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
        return companyList;

    }

    @Override
    public List<Company> fetchByName(String name) {
        TypedQuery<Company> findByNameQuery = getEntityManager().createNamedQuery("Company.findByName",
                Company.class);
        findByNameQuery.setParameter("name", name);
        return findByNameQuery.getResultList();
    }

    @Override
    public Company fetchById(long id) {
        Company company = getEntityManager().find(Company.class, id);
        if (company == null) {
            throw new EntityNotFoundException(String.format("Company with id %d was not found", id));
        }
        return company;
    }

    @Override
    @Transactional
    public Company create(CompanyDto dto) {
        Company entity = new Company();
        entity.setName(dto.getName());
        entity.setStreet(dto.getStreet());
        entity.setBuildingNo(dto.getBuildingNo());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        List<Location> locations = dto.getLocations().stream()
                .map(locationDto -> LocationDto.toEntity(locationDto, entity))
                .collect(toList());
        entity.setLocations(locations);
        getEntityManager().persist(entity);
        return entity;
    }

}
