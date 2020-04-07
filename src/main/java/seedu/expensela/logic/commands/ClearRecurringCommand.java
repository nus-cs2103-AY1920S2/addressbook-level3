package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.model.Model;

/**
 * Clear all recurring transactions
 */
public class ClearRecurringCommand extends Command {

    public static final String COMMAND_WORD = "clearrecurring";
    public static final String MESSAGE_SUCCESS = "Recurring transactions for ExpenseLa have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearRecurringTransactions();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
