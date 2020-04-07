package seedu.address.model;

/**
 * Represents an object that can produce its independent copy.
 */
public interface Copyable<T> {
    public T copy();
}
