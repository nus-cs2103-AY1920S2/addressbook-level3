package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! Type help for program usage instructions.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The customer index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d customers listed!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This customer already exists in the system";

    public static final String MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX = "The product index provided is invalid.";
    public static final String MESSAGE_INVALID_PRODUCT_QUANTITY = "There are only %1$d %2$s(s) left!";
    public static final String MESSAGE_ZERO_PRODUCT_QUANTITY = "There are no %2$ss left!";
    public static final String MESSAGE_PRODUCTS_LISTED_OVERVIEW = "%1$d products listed!";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the system";
    public static final String MESSAGE_INVALID_INITIAL_QUANTITY = "You cannot add an product with 0 quantity!";

    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "%1$d transactions listed!";
    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX =
            "The transaction index provided is invalid.";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction already exists in the system";

    public static final String MESSAGE_INVALID_THRESHOLD_AMOUNT = "Threshold amount must be more than 0!";

    public static final String MESSAGE_MULTIPLE_SAME_PREFIX = "Please only use each prefix once! \n%1$s";
}
