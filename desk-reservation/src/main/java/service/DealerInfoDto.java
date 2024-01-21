package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealerInfoDto {
    private String name;
    private String city;
    private String address;
    private String zipcode;
}
