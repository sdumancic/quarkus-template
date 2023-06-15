package user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto {

    private int page;
    private int pageSize;
    private long totalRecords;
    private String query;
}
