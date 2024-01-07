package util;

import domain.model.Company;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CriteriaOrderBuilder {
    public static List<Order> buildFromSortOrder(List<SortOrder> sortOrderList, CriteriaBuilder criteriaBuilder, Root<Company> root) {
        List<Order> orderList = new ArrayList<>();
        sortOrderList.forEach(el -> {
            if (el.getDirection() == SortDirection.ASC) {
                orderList.add(criteriaBuilder.asc(root.get(el.getAttribute())));
            } else {
                orderList.add(criteriaBuilder.desc(root.get(el.getAttribute())));
            }
        });
        return orderList;
    }
}
