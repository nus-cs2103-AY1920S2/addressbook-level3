package nasa.model;

/**
 * Allows activities to auto-repeat.
 * @param <T>
 */
public interface Regenerable<T> {
    T regenerate();
}
