package seedu.expensela.testutil;

import static seedu.expensela.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_REMARK;

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
        sb.append(PREFIX_AMOUNT + transaction.getAmount().toString().substring(3) + " ");
        sb.append(PREFIX_DATE + transaction.getDate().transactionDate.toString() + " ");
        sb.append(PREFIX_REMARK + transaction.getRemark().transactionRemark + " ");
        sb.append(PREFIX_CATEGORY + transaction.getCategory().transactionCategory + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code editTransaction}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditCommand.EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.transactionName).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString().substring(3))
                .append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.transactionDate).append(" "));
        descriptor.getRemark().ifPresent(remark ->
                sb.append(PREFIX_REMARK).append(remark.transactionRemark).append(" "));
        descriptor.getCategory().ifPresent(category ->
                sb.append(PREFIX_CATEGORY).append(category.transactionCategory).append(" "));
        return sb.toString();
    }
}
