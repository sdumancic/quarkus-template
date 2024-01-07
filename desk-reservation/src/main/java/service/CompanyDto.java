package service;

import domain.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompanyDto {
    private long id;
    private String name;
    private String street;
    private String buildingNo;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private List<LocationDto> locations;

    public static CompanyDto toDto(Company company) {
        return new CompanyDto(company.getId(), company.getName(), company.getStreet(), company.getBuildingNo(), company.getPostalCode(),
                company.getCity(), company.getState(), company.getCountry(), company.getLocations().stream().map(LocationDto::toDto).toList());
    }
}
