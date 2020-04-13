package seedu.address.testutil;

import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.wallet.WalletBudgetCommand;
import seedu.address.logic.commands.wallet.WalletEditCommand;
import seedu.address.logic.commands.wallet.WalletExpenseCommand;
import seedu.address.logic.commands.wallet.WalletIncomeCommand;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.model.transaction.Transaction;



/**
 * A utility class for Wallet.
 */
public class WalletUtil {

    /**
     * Returns an expense command string for adding the {@code expense}.
     */
    public static String getExpenseCommand(Expense expense) {
        return WALLET_COMMAND_TYPE + " " + WalletExpenseCommand.COMMAND_WORD + " " + getTransactionDetails(expense);
    }

    /**
     * Returns an income command string for adding the {@code income}.
     */
    public static String getIncomeCommand(Income income) {
        return WALLET_COMMAND_TYPE + " " + WalletIncomeCommand.COMMAND_WORD + " " + getTransactionDetails(income);
    }

    /**
     * Returns a budget command string for adding the {@code budget}
     */
    public static String getBudgetCommand(Budget budget) {
        StringBuilder sb = new StringBuilder();
        sb.append(WALLET_COMMAND_TYPE + " " + WalletBudgetCommand.COMMAND_WORD + " ");
        sb.append(PREFIX_AMOUNT + budget.getAmount().inDollars() + " ");
        sb.append(PREFIX_MONTH + String.valueOf(budget.getMonth().getValue()) + " ");
        sb.append(PREFIX_YEAR + budget.getYear().toString());
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(WalletEditCommand.EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT)
                .append(amount.inDollars()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE)
                .append(date.getInputFormat()).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_NAME)
                .append(description.description).append(" "));
        descriptor.getTag().ifPresent(tag -> sb.append(PREFIX_TAG).append(tag.tagName).append(" "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s
     * details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + transaction.getDescription().description + " ");
        sb.append(PREFIX_AMOUNT + transaction.getAmount().inDollars() + " ");
        sb.append(PREFIX_DATE + transaction.getDate().getInputFormat() + " ");
        sb.append(PREFIX_TAG + transaction.getTag().tagName);
        return sb.toString();
    }
}
