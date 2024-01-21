package service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WorkOrderInfoDto {
    private String orderNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dropOfDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickupDate;
}
