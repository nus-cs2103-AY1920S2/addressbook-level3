package seedu.expensela.testutil;

import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code transaction}.
     RPRE
    public static String getAddCommand(Transaction transaction) {
        return AddCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + transaction.getName().transactionName + " ");
        sb.append(PREFIX_AMOUNT + transaction.getAmount().toString() + " ");
        sb.append(PREFIX_DATE + transaction.getDate().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTransactionDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditCommand.EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.transactionName).append(" "));
        descriptor.getAmount().ifPresent(phone -> sb.append(PREFIX_AMOUNT).append(phone.toString()).append(" "));
        descriptor.getDate().ifPresent(address -> sb.append(PREFIX_DATE).append(address.value).append(" "));
        return sb.toString();
    }
}
