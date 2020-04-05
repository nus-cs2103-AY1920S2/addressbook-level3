package seedu.expensela.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_FILTER = "Filter keyword is invalid! \nEnter command in format: "
            + "filter c/<CATEGORY> m/<MONTH> where <MONTH> is in YYYY-MM, starting from year 2000 \n"
            + "example: filter c/FOOD m/2020-01\n"
            + "example: filter c/GROCERIES m/ALL\n"
            + "example: filter m/2021-12";
    public static final String MESSAGE_WORD_NOT_FOUND = "Unable to find any matches! Enter 'list' to go back";
    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX = "The transaction index "
            + "provided is invalid";
    public static final String MESSAGE_TRANSACTION_LISTED_OVERVIEW = "%1$d transactions listed!";


}
