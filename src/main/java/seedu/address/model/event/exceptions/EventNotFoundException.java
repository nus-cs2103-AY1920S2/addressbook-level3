package seedu.address.model.event.exceptions;
/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Did not find the specified event");
    }
}
