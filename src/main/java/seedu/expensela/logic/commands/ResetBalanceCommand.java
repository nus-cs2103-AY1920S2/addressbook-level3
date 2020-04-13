package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;

import javafx.collections.ObservableList;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Balance;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.Transaction;

/**
 * Reset balance to 0 in expenseLa
 */
public class ResetBalanceCommand extends Command {

    public static final String COMMAND_WORD = "resetbalance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Total balance is now %.2f.";

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Transaction> transactionList = model.getExpenseLa().getTransactionList();
        double balance = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getAmount().positive) {
                balance += transactionList.get(i).getAmount().transactionAmount;
            } else {
                balance -= transactionList.get(i).getAmount().transactionAmount;
            }
        }
        model.updateTotalBalance(new Balance(df2.format(balance)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, balance));
    }

}
