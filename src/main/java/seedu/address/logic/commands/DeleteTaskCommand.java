package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a task from the calendar or from certain module in profile tab.
 */
public abstract class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "taskDelete";

    public abstract CommandResult execute(Model model) throws CommandException;
}
