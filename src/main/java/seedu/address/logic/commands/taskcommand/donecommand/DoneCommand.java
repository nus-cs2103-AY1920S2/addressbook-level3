package seedu.address.logic.commands.taskcommand.donecommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Mark a task as done from the calendar or from certain module in profile tab.
 */
public abstract class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark deadline as done. Format of input should be:"
            + " done index/{num} "
            + "Example: done index/2\n"
            + "or\n"
            + COMMAND_WORD + ": mark a module task as done "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_TASK_INDEX + "index "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_TASK_INDEX + "1";
    public abstract CommandResult execute(Model model) throws CommandException;
}
