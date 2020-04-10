package team.easytravel.model.trip.exception;

/**
 * Signals that the operation is illegal.
 */
public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException(String message) {
        super(message);
    }
}
