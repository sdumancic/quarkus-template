package util;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class PageableResource {

    @Inject
    EntityManager em;

    protected EntityManager getEntityManager() {
        return this.em;
    }

    private CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    public CriteriaQuery<Long> getCountQuery(Class aClass, List<FilterParams> filterParamsList) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root countRoot = countQuery.from(aClass);

        List<Predicate> predicates2 = new ArrayList<>();
        filterParamsList.forEach(filterParam -> {

            Object value = filterParam.getParamValue();
            if (filterParam.getParamType() == String.class) {
                value = String.class.cast(filterParam.getParamValue());
            } else if (filterParam.getParamType() == Integer.class) {
                value = Integer.class.cast(filterParam.getParamValue());
            }

            switch (filterParam.getOperator()) {
                case Eq -> predicates2.add(criteriaBuilder.equal(countRoot.get(filterParam.getParamName()), value));
                case Like ->
                        predicates2.add(criteriaBuilder.like(countRoot.get(filterParam.getParamName()), value.toString()));
                case Lt ->
                        predicates2.add(criteriaBuilder.lt(countRoot.get(filterParam.getParamName()), (Number) value));
                case Lte ->
                        predicates2.add(criteriaBuilder.le(countRoot.get(filterParam.getParamName()), (Number) value));
                case Gt ->
                        predicates2.add(criteriaBuilder.gt(countRoot.get(filterParam.getParamName()), (Number) value));
                case Gte ->
                        predicates2.add(criteriaBuilder.ge(countRoot.get(filterParam.getParamName()), (Number) value));
                case Neq -> predicates2.add(criteriaBuilder.notEqual(countRoot.get(filterParam.getParamName()), value));
            }
        });

        Predicate finalPredicate2 = criteriaBuilder.and(predicates2.toArray(new Predicate[0]));
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(finalPredicate2);
        return countQuery;
    }

    public CriteriaQuery getSelectQuery(Class aClass, List<FilterParams> filterParamsList, List<SortOrder> sortOrders) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(aClass);
        Root root = query.from(aClass);

        List<Predicate> predicates = new ArrayList<>();
        filterParamsList.forEach(filterParam -> {
            Class<?> theClass = null;
            Object value = filterParam.getParamValue();
            if (filterParam.getParamType() == String.class) {
                value = String.class.cast(filterParam.getParamValue());
            } else if (filterParam.getParamType() == Integer.class) {
                value = Integer.class.cast(filterParam.getParamValue());
            }

            switch (filterParam.getOperator()) {
                case Eq -> predicates.add(criteriaBuilder.equal(root.get(filterParam.getParamName()), value));
                case Like ->
                        predicates.add(criteriaBuilder.like(root.get(filterParam.getParamName()), value.toString()));
                case Lt -> predicates.add(criteriaBuilder.lt(root.get(filterParam.getParamName()), (Number) value));
                case Lte -> predicates.add(criteriaBuilder.le(root.get(filterParam.getParamName()), (Number) value));
                case Gt -> predicates.add(criteriaBuilder.gt(root.get(filterParam.getParamName()), (Number) value));
                case Gte -> predicates.add(criteriaBuilder.ge(root.get(filterParam.getParamName()), (Number) value));
                case Neq -> predicates.add(criteriaBuilder.notEqual(root.get(filterParam.getParamName()), value));
            }

        });

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        query.where(finalPredicate);
        List<Order> orderList = CriteriaOrderBuilder.buildFromSortOrder(sortOrders, criteriaBuilder, root);
        query.orderBy(orderList);
        return query;

    }
}
