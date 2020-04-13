package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.TaskAction;

/**
 * Adds a module to Mod Manager.
 */
public class TaskAddCommand extends TaskCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_ADD
            + ": Adds a task to Mod Manager. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + " MOD_CODE "
            + PREFIX_DESCRIPTION + " TASK_DESCRIPTION "
            + "[" + PREFIX_ON + " DD/MM/YYYY] "
            + "[" + PREFIX_AT + " hh:mm] \n"
            + "Example: " + COMMAND_GROUP_TASK + " " + COMMAND_WORD_ADD + " "
            + PREFIX_MODULE_CODE + " CS3230 "
            + PREFIX_DESCRIPTION + " Programming Assignment 2 "
            + PREFIX_ON + " 01/04/2020 "
            + PREFIX_AT + " 23:59\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Mod Manager. "
            + "Please choose another description, or another time.";
    public static final String MESSAGE_NOT_ADDED = "Unable to add this task. "
            + "There is no information received. Please try again!";
    public static final String MESSAGE_MODULE_NOT_EXISTENT = "%s is not an existing module!";
    private final Task toAdd;

    /**
     * Creates a TaskAddCommand to add the specified {@code Task}
     */
    public TaskAddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        ModuleCode taskMod = toAdd.getModuleCode();
        Optional<Module> mod = model.findModule(taskMod);

        if (mod.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_EXISTENT, taskMod.toString()));
        }

        model.addTask(toAdd);
        TaskAction addTaskAction = new TaskAction(toAdd, DoableActionType.ADD);
        model.addAction(addTaskAction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandType.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskAddCommand) // instanceof handles nulls
                && toAdd.equals(((TaskAddCommand) other).toAdd);
    }

}
