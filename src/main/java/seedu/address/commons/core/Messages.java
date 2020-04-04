package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String NEWLINE = System.lineSeparator();

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid.";
    public static final String MESSAGE_INVALID_RETURN_DISPLAYED_INDEX = "The return order index provided is invalid.";
    public static final String MESSAGE_MISSING_FLAG = "Please provide flag -o or -r";
    public static final String MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP = "Seems like you gave the wrong combination to "
        + "edit the date!" + NEWLINE + "Please provide flag -o with dts/ to edit Order Delivery date"
        + " or -r with rts/ to edit Return order delivery date."
        + NEWLINE + "Any other combinations are not allowed!";
    public static final String MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER =
        "There is no cash on delivery field in Return order please check your input.";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%d order(s) listed!";
    public static final String MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW = "%d return order(s) listed!";

}
