package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WacItemInfoDto {
    private String name;
    private WacItemStatus status;

    private List<WacItemRepairInfoDto> repairs;

    public enum WacItemStatus {
        OK,
        NOT_OK
    }
}


