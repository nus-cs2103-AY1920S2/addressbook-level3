package seedu.address.model.step.exceptions;

/**
 * Signals that the operation is unable to find the specified step.
 */
public class StepNotFoundException extends RuntimeException {
    public StepNotFoundException() {
        super("Step not found");
    }
}
