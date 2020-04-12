package seedu.address.logic.commands.taskcommand.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETASK_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.ModuleTask;

/**
 * Add a new module task to a specific module which is already recorded in the program.
 */
public class ModuleTaskCommand extends Command {

    public static final String COMMAND_WORD = "moduleTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to one of your modules "
            + "Parameters: "
            + PREFIX_TASK_DESC + "Description "
            + PREFIX_MODULE_CODE + "Module related "
            + PREFIX_MODULETASK_TIMING + "Timing of the task (in the format of {YYYY-MM-DD})"
            + PREFIX_PRIORITY + "Priority "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_DESC + "Tutorial 08 "
            + PREFIX_MODULE_CODE + "CS2030 "
            + PREFIX_MODULETASK_TIMING + "30-04-2020 "
            + PREFIX_PRIORITY + "5 ";

    public static final String MESSAGE_SUCCESS = "New task added successfully\nList of tasks for %s\n%s";
    public static final String MESSAGE_NO_SUCH_MODULE = "This module does not exist, "
            + "maybe you can add the module first";

    private final ModuleTask toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ModuleTaskCommand(ModuleTask task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(toAdd.getModuleRelated())) {
            throw new CommandException(MESSAGE_NO_SUCH_MODULE);
        }

        model.addModuleTask(toAdd);
        model.addDeadlineTask(toAdd);
        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);
        model.sortTaskList();

        String tasksInfo = model.getModuleTaskInfo(toAdd.getModuleRelated());

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getModuleRelated(), tasksInfo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleTaskCommand // instanceof handles nulls
                && toAdd.equals(((ModuleTaskCommand) other).toAdd));
    }

}
