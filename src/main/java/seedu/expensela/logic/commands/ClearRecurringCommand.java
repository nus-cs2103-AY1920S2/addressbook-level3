package seedu.expensela.logic.commands;

import seedu.expensela.model.Model;

import static java.util.Objects.requireNonNull;

public class ClearRecurringCommand extends Command {

    public static final String COMMAND_WORD = "clearrecurring";
    public static final String MESSAGE_SUCCESS = "Recurring transactions for ExpenseLa have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetGlobalData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
