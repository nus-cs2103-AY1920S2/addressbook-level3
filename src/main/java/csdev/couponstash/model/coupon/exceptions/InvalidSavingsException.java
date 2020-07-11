package csdev.couponstash.model.coupon.exceptions;

/**
 * This Exception will be thrown when some error is
 * detected when trying to compare Savings
 */
public class InvalidSavingsException extends RuntimeException {
    public InvalidSavingsException() {
        super("Invalid savings format detected, cannot be compared due to"
                + " savings not having any data");
    }

    public InvalidSavingsException(String message) {
        super(message);
    }
}
