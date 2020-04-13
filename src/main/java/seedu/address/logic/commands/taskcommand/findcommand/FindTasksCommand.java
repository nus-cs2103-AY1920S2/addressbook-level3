package seedu.address.logic.commands.taskcommand.findcommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETASK_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Find tasks in calendar by specific key word (category/date/module code).
 */
public abstract class FindTasksCommand extends Command {
    public static final String COMMAND_WORD = "findTask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find tasks as required\n"
            + COMMAND_WORD + ": find tasks related to given module code "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103T "
            + "or\n"
            + COMMAND_WORD + ": find tasks related to given category "
            + "Parameters: "
            + PREFIX_TASK_CAT + "CATEGORY"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_CAT + "School work "
            + "or\n"
            + COMMAND_WORD + ": find tasks related to given date "
            + "Parameters: "
            + PREFIX_MODULETASK_TIMING + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULETASK_TIMING + "01-01-2020";

    public abstract CommandResult execute(Model model) throws CommandException;
}
