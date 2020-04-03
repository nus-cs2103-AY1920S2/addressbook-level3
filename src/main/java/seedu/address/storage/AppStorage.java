package seedu.address.storage;

import seedu.address.logic.conditions.Conditions;

import java.util.ArrayList;

public interface AppStorage<T> {

    public void create(T templateClass);

    public void delete(T templateClass);

    public void update(T oldTemplateClass, T newTemplateClass);

    /**
     * Given a condition, return all valid objects
     * @param   cond            {@code Condition} interface
     *
     * @return  ArrayList<T>    Filtered results
     */
    public ArrayList<T> search(Conditions cond);

    public ArrayList<T> search();

    /**
     * Performs an aggregated search over records passed in to identify groups of records which satisfies a condition
     * @param   records         Batch of records to evaluate
     *          cond            {@code Condition} interface
     *
     * @return  ArrayList<T>    Filtered results
     */
    public ArrayList<T> searchAggregated(ArrayList<T> records, Conditions cond);
}
