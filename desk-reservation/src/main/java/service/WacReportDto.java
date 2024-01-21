package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WacReportDto {
    private WorkOrderInfoDto workOrderInfo;
    private UserInfoDto userInfo;
    private DealerInfoDto dealerInfo;
    private VehicleInfoDto vehicleInfo;
    private CustomerInfoDto customerInfo;
    private WacDetailsDto details;

}
