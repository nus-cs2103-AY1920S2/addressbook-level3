package fithelper.model.weight.exceptions;

/**
 * Signals that the operation will result in duplicate Weight Records
 * (Weight Records are considered duplicates if they have the same date).
 */
public class DuplicateWeightException extends RuntimeException {
    public DuplicateWeightException() {
        super("Operation would result in duplicate weight records!");
    }
}
