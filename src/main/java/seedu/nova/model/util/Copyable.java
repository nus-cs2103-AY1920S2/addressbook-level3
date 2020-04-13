package seedu.nova.model.util;

/**
 * Objects that can be deep copied
 * @param <T> the object
 */
public interface Copyable<T> {
    public T getCopy();
}
