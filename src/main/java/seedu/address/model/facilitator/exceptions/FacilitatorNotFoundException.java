package seedu.address.model.facilitator.exceptions;

/**
 * Signals that the operation is unable to find the specified facilitator.
 */
public class FacilitatorNotFoundException extends RuntimeException {
    public FacilitatorNotFoundException() {
        super("Operation is unable to find the specified facilitator.");
    }
}
