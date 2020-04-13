package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;

/**
 * Clears the expensela.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ExpenseLa has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setExpenseLa(new ExpenseLa());
        model.setGlobalData(new GlobalData());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
