package seedu.address.storage;

import seedu.address.logic.aggregators.Aggregators;
import seedu.address.logic.conditions.Conditions;

import java.util.ArrayList;

public interface AppStorage<T> {

    public void create(T templateClass);

    public void delete(T templateClass);

    public void update(T oldTemplateClass, T newTemplateClass);

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
