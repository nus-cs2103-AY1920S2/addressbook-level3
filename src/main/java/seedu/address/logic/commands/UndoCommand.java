package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Managed to undo previous action!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
