package domain.repository;

import domain.model.Company;
import service.CompanyDto;
import util.PageableSortableRequest;

import java.util.List;

public interface CompanyRepository {

    List<Company> fetchAll(PageableSortableRequest request, String name, String city, String country);

    List<Company> fetchByName(String name);

    Company fetchById(long id);

    Company create(CompanyDto dto);

}
