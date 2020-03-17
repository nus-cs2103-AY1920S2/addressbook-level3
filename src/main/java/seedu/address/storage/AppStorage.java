package seedu.address.storage;

import seedu.address.logic.conditions.Conditions;

import java.util.ArrayList;

public interface AppStorage<T> {
    /**
     * Any initialization of storage class to be settled here
     */
    public void init();

    public void create(T templateClass);

    public void delete(T templateClass);

    public void update(T oldTemplateClass, T newTemplateClass);

    /**
     * Given a condition, return all valid objects
     * @param   cond            {@code Condition} interface
     * @return  ArrayList<T>    Filtered results
     */
    public ArrayList<T> search(Conditions cond);
}
