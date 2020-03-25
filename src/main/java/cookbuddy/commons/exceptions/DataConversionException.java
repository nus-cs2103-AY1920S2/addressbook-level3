package cookbuddy.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
@SuppressWarnings("serial")
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
