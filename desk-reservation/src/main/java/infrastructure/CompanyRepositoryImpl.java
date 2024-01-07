package infrastructure;

import domain.model.Company;
import domain.model.Location;
import domain.repository.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import service.CompanyDto;
import service.LocationDto;
import util.CriteriaOrderBuilder;
import util.PageableSortableRequest;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CompanyRepositoryImpl implements CompanyRepository {

    @Inject
    EntityManager em;

    @Override
    public List<Company> fetchAll(PageableSortableRequest request, String name, String city, String country) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Company> query = criteriaBuilder.createQuery(Company.class);

        Root<Company> root = query.from(Company.class);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            predicates.add(criteriaBuilder.like(root.get("name"), name.concat("%")));
        }
        if (StringUtils.isNotBlank(city)) {
            predicates.add(criteriaBuilder.equal(root.get("city"), city));
        }
        if (StringUtils.isNotBlank(country)) {
            predicates.add(criteriaBuilder.equal(root.get("country"), country));
        }
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.where(finalPredicate);

        List<Order> orderList = CriteriaOrderBuilder.buildFromSortOrder(request.getSort(), criteriaBuilder, root);

        query.orderBy(orderList);

        int firstResult = request.getFirstResult();
        int pageSize = request.getPageSize();
        List<Company> companyList = em.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
        return companyList;

    }

    @Override
    public List<Company> fetchByName(String name) {
        TypedQuery<Company> findByNameQuery = em.createNamedQuery("Company.findByName",
                Company.class);
        findByNameQuery.setParameter("name", name);
        return findByNameQuery.getResultList();
    }

    @Override
    public Company fetchById(long id) {
        Company company = em.find(Company.class, id);
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
        em.persist(entity);
        return entity;
    }

}
