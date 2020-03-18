package seedu.expensela.testutil;

import static seedu.expensela.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code transaction}.
     */
    public static String getAddCommand(Transaction transaction) {
        return AddCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + transaction.getName().transactionName + " ");
        sb.append(PREFIX_PHONE + transaction.getAmount().toString() + " ");
        sb.append(PREFIX_ADDRESS + transaction.getDate().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code editTransaction}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditCommand.editTransaction descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.transactionName).append(" "));
        descriptor.getAmount().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.toString()).append(" "));
        descriptor.getDate().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }
}
