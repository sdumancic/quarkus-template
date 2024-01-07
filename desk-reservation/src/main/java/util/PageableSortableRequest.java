package util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class PageableSortableRequest {
    private int pageNo;
    private int firstResult;
    private int pageSize;
    private String sort;

    public PageableSortableRequest() {
        this.pageNo = 0;
        this.firstResult = 0;
        this.pageSize = 10;
    }

    public PageableSortableRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.firstResult = pageNo * pageSize;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param sort     (sort=+id,-name,+surname)
     */
    public PageableSortableRequest(int pageNo, int pageSize, String sort) {
        this(pageNo, pageSize);
        this.sort = sort;
    }

    public List<SortOrder> getSort() {
        if (StringUtils.isEmpty(sort)) {
            return null;
        }
        String[] elements = sort.split(",");
        List<SortOrder> collection = Arrays.stream(elements).map(el -> {
            SortOrder sortOrder = new SortOrder();
            SortDirection sortDirection = SortDirection.ASC;
            String sortAttribute = el;
            char firstCharacter = el.charAt(0);
            if (firstCharacter == '-') {
                sortDirection = SortDirection.DESC;
                sortAttribute = el.substring(1).trim();
            } else if (firstCharacter == '+') {
                sortDirection = SortDirection.ASC;
                sortAttribute = el.substring(1).trim();
            } else {
                sortDirection = SortDirection.ASC;
                sortAttribute = el.trim();
            }
            sortOrder.setAttribute(sortAttribute);
            sortOrder.setDirection(sortDirection);
            return sortOrder;
        }).collect(toList());
        return collection;
    }

    @Override
    public String toString() {
        return "PageableSortableRequest{" +
                "pageNo=" + pageNo +
                ", firstResult=" + firstResult +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                '}';
    }
}
