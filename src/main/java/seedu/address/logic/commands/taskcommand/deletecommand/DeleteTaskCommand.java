package seedu.address.logic.commands.taskcommand.deletecommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a task from the calendar or from certain module in profile tab.
 */
public abstract class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "taskDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete deadline. Format of input should be:"
            + " taskDelete index/{num} "
            + "Example: taskDelete index/2\n"
            + "or\n"
            + COMMAND_WORD + ": Delete a module task "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_TASK_INDEX + "index "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_TASK_INDEX + "1";


    public abstract CommandResult execute(Model model) throws CommandException;
}
