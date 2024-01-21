package util;

import java.util.ArrayList;
import java.util.List;

public class FilterQuery {

    /*
     * a and b or (c and d and (e or (f and g)))
     *
     * a.and(b).or(
     *
     * */
    private List<Object> filterQueue;
    private List<String> operatorQueue;

    public List<Object> getFilterQueue() {
        return filterQueue;
    }

    public List<String> getOperatorQueue() {
        return operatorQueue;
    }

    public FilterQuery(FilterParams param) {
        this.filterQueue = new ArrayList<>();
        this.operatorQueue = new ArrayList<>();
        this.filterQueue.add(param);
        this.operatorQueue.add("and");
    }

    public FilterQuery(FilterQuery query) {
        this.filterQueue = new ArrayList<>();
        this.operatorQueue = new ArrayList<>();
        this.filterQueue.add(query);
        this.operatorQueue.add("and");
    }

    public FilterQuery and(FilterParams param) {
        this.filterQueue.add(param);
        this.operatorQueue.add("and");
        return this;
    }

    public FilterQuery and(FilterQuery query) {
        this.filterQueue.add(query);
        this.operatorQueue.add("and");
        return this;
    }

    public FilterQuery or(FilterParams param) {
        this.filterQueue.add(param);
        this.operatorQueue.add("or");
        return this;
    }

    public FilterQuery or(FilterQuery query) {
        this.filterQueue.add(query);
        this.operatorQueue.add("or");
        return this;
    }

    @Override
    public String toString() {
        return "FilterQuery{" +
                "filterQueue=" + filterQueue.size() + " " + filterQueue +
                '}';
    }
}
