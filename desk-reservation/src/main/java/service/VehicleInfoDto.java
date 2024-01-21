package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class VehicleInfoDto {
    private String licencePlate;
    private String brand;
    private String model;
    private Integer mileage;
}
