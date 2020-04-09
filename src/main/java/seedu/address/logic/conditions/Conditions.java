package seedu.address.logic.conditions;

public interface Conditions<T> {

    /**
     * Given a class, enforce logic to see if certain properties of the class meets the condition
     * This may be used in conjunction with filtering logic
     */
    public Boolean satisfies(T objToTest);
}
