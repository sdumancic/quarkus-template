package util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilterParams {

    FilterParams(String paramName, Object paramValue, Class paramType, ParamOperator operator) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramType = paramType;
        this.operator = operator;
    }

    public static FilterParamsBuilder builder() {
        return new FilterParamsBuilder();
    }

    public enum ParamOperator {
        Eq, Neq, Like, Gt, Gte, Lt, Lte
    }

    private String paramName;
    private Object paramValue;
    private Class paramType;
    private ParamOperator operator;

    public static class FilterParamsBuilder {
        private String paramName;
        private Object paramValue;
        private Class paramType;
        private ParamOperator operator;

        FilterParamsBuilder() {
        }

        public FilterParamsBuilder paramName(String paramName) {
            this.paramName = paramName;
            return this;
        }

        public FilterParamsBuilder paramValue(Object paramValue) {
            this.paramValue = paramValue;
            return this;
        }

        public FilterParamsBuilder paramType(Class paramType) {
            this.paramType = paramType;
            return this;
        }

        public FilterParamsBuilder operator(ParamOperator operator) {
            this.operator = operator;
            return this;
        }

        public FilterParams build() {
            return new FilterParams(this.paramName, this.paramValue, this.paramType, this.operator);
        }

        public String toString() {
            return "FilterParams.FilterParamsBuilder(paramName=" + this.paramName + ", paramValue=" + this.paramValue + ", paramType=" + this.paramType + ", operator=" + this.operator + ")";
        }
    }
}
