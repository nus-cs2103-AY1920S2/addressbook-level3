package seedu.eylah.expensesplitter.logic.commands;

//import static java.util.Objects.requireNonNull;

/**
 *  Lists the current receipt to the user.
 */
public class ListReceiptCommand {

    public static final String COMMAND_WORD = "listreceipt";
    public static final String MESSAGE_SUCCESS = "Listed current receipt containing all Items and Person(s) involved"
            + "in splitting it.";
}
