package util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PredicateGenerator {

    public static Predicate generatePredicate(CriteriaBuilder criteriaBuilder, Root<?> root, FilterQuery filterQuery) {
        if (filterQuery == null || filterQuery.getFilterQueue().isEmpty()) {
            return null;
        }
        List<Object> filterQueue = filterQuery.getFilterQueue();
        List<String> operatorQueue = filterQuery.getOperatorQueue();

        Predicate predicate = null;

        for (int i = 0; i < filterQueue.size(); i++) {
            Object filterParam = filterQueue.get(i);
            String operator = operatorQueue.get(i);
            if (filterParam instanceof FilterParams) {
                Predicate paramPredicate = generateParamPredicate(criteriaBuilder, root, (FilterParams) filterParam);
                predicate = combinePredicates(criteriaBuilder, predicate, paramPredicate, operator);
            } else if (filterParam instanceof FilterQuery) {
                Predicate subQueryPredicate = generatePredicate(criteriaBuilder, root, (FilterQuery) filterParam);
                predicate = combinePredicates(criteriaBuilder, predicate, subQueryPredicate, operator);
            }
        }

        return predicate;
    }

    private static Predicate generateParamPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, FilterParams filterParam) {
        return criteriaBuilder.equal(root.get(filterParam.getParamName()), filterParam.getParamValue());
    }

    private static Predicate combinePredicates(CriteriaBuilder criteriaBuilder, Predicate predicate1, Predicate predicate2, String operator) {
        if ("init".equals(operator)) {
            return predicate1;
        } else if ("and".equals(operator)) {
            return (predicate1 == null) ? predicate2 : criteriaBuilder.and(predicate1, predicate2);
        } else if ("or".equals(operator)) {
            return (predicate1 == null) ? predicate2 : criteriaBuilder.or(predicate1, predicate2);
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
