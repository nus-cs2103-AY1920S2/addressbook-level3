package seedu.address.storage;

import seedu.address.logic.aggregators.Aggregators;
import seedu.address.logic.conditions.Conditions;

import java.util.ArrayList;

public interface AppStorage<T> {

    public ArrayList<T> search(T obj);

    public void create(T obj) throws Exception;

    public void delete(ArrayList<T> objs);

    public void delete(T obj);

    public void update(T objPast, T objNew);

    /**
     * Performs an aggregated search over records passed in to identify groups of records which satisfies a condition
     * @param   cond            {@code Condition} interface
     *          agg             Aggregation logic
     *
     * @return  ArrayList<T>    Filtered results
     */
    public ArrayList<T> search(Conditions cond, Aggregators agg);

    /**
     * Given a condition, return all valid objects
     * @param   cond            {@code Condition} interface
     *
     * @return  ArrayList<T>    Filtered results
     */
    public ArrayList<T> search(Conditions cond);

    public ArrayList<T> search();
}
