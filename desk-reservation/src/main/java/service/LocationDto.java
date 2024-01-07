package service;

import domain.model.Company;
import domain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class LocationDto {
    private long id;
    private String name;
    private String street;
    private String buildingNo;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;

    public static LocationDto toDto(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getStreet(), location.getBuildingNo(), location.getPostalCode(), location.getCity(), location.getState(), location.getCountry(), location.getLatitude(), location.getLongitude());
    }

    public static Location toEntity(LocationDto dto, Company company) {
        Location entity = new Location();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStreet(dto.getStreet());
        entity.setBuildingNo(dto.getBuildingNo());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setCompany(company);
        return entity;
    }

}
