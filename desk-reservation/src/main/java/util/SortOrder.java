package util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortOrder {
    private String attribute;
    private SortDirection direction;

    @Override
    public String toString() {
        return "SortOrder{" +
                "attribute='" + attribute + '\'' +
                ", direction=" + direction +
                '}';
    }
}
