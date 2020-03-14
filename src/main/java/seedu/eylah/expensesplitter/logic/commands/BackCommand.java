package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;

/**
 * Used to go back main menu.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_SUCCESS = "Returned to Main Menu.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.backToMainMenu();
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
