package service;

import domain.model.Company;
import domain.model.Location;
import domain.repository.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import util.PageableSortableRequest;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CompanyService {

    @Inject
    CompanyRepository companyRepository;

    Function<Company, CompanyDto> mapEntityToDto = CompanyDto::toDto;

    Function<Location, LocationDto> mapLocationEntityToDto = LocationDto::toDto;

    public CompanyDto createCompany(CompanyDto dto) {
        Company company = companyRepository.create(dto);
        List<LocationDto> locationDto = company.getLocations().stream().map(mapLocationEntityToDto).collect(toList());
        return new CompanyDto(company.getId(), company.getName(), company.getStreet(), company.getBuildingNo(), company.getPostalCode(), company.getCity(), company.getState(), company.getCountry(), locationDto);
    }

    public List<CompanyDto> listAll(PageableSortableRequest request, String name, String city, String country) {
        List<Company> companies = companyRepository.fetchAll(request, name, city, country);
        return companies.stream().map(mapEntityToDto).collect(toList());
    }

}
