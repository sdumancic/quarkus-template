package util;

import org.junit.jupiter.api.Test;


class FilterQueryTest {

    @Test
    void testFilterQuery() {
        FilterParams p1 = FilterParams.builder()
                .paramType(String.class)
                .paramName("a")
                .paramValue("a")
                .operator(FilterParams.ParamOperator.Like).build();
        FilterParams p2 = FilterParams.builder()
                .paramType(String.class)
                .paramName("b")
                .paramValue("b")
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p3 = FilterParams.builder()
                .paramType(String.class)
                .paramName("c")
                .paramValue("c")
                .operator(FilterParams.ParamOperator.Eq).build();
        FilterParams p4 = FilterParams.builder()
                .paramType(String.class)
                .paramName("d")
                .paramValue("d")
                .operator(FilterParams.ParamOperator.Eq).build();

        // p1 or (p2 and p3 or (p4 or p1))
        FilterQuery filterQuery = new FilterQuery(p1).or(new FilterQuery(p2).and(p3).or(new FilterQuery(p4).or(p1)));

    }
}
