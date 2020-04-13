package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.task.TaskEditCommand.MESSAGE_MODULE_NOT_FOUND;
import static seedu.address.logic.commands.task.TaskEditCommand.MESSAGE_TASK_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_ID;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskNumManager;

/**
 * Marks a task as done in Mod Manager.
 */
public class TaskMarkAsDoneCommand extends TaskCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_DONE
            + ": Marks a task as done in Mod Manager. \n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + " MOD_CODE "
            + PREFIX_TASK_ID + " ID_NUMBER \n"
            + "Example: " + COMMAND_GROUP_TASK + " " + COMMAND_WORD_DONE + " "
            + PREFIX_MODULE_CODE + " CS3230 "
            + PREFIX_TASK_ID + " 913 ";

    public static final String MESSAGE_SUCCESS = "Congratulations! Task has been marked as done: %1$s";
    public static final String MESSAGE_MODULE_CODE_NOT_EXISTENT = "The module code is not found! Please try again.";

    public static final String MESSAGE_TASK_ID_INVALID = "The Task's ID_NUMBER is not valid. "
            + "It cannot contain letters or special characters.";
    public static final String MESSAGE_TASK_ID_NOT_EXISTENT = "The Task's ID_NUMBER is not found! Please try again.";
    public static final String MESSAGE_TASK_ALREADY_DONE = "Task of module %s with ID %s is already done. "
        + "Please try again with another task!";

    private final String moduleCode;
    private final int taskNum;
    /**
     * Creates a TaskMarkAsDoneCommand to add the specified {@code Task}
     */
    public TaskMarkAsDoneCommand(String moduleCode, String taskNum) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
        this.taskNum = Integer.parseInt(taskNum);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> current = model.getFilteredTaskList();

        if (!model.hasModuleCode(moduleCode)) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode));
        }

        if (!TaskNumManager.doesNumExist(new ModuleCode(moduleCode), taskNum)) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND, moduleCode, taskNum));
        }

        assert model.hasModuleCode(moduleCode) : "The module should be already available in Mod Manager";
        assert TaskNumManager.doesNumExist(new ModuleCode(moduleCode), taskNum) : "The Task ID is valid for the module";

        Task taskToEdit = current.stream().reduce(null, (x, y) -> {
            if (y.getTaskNum() == this.taskNum) {
                return y;
            } else {
                return x;
            }
        });

        assert (taskToEdit != null) : "The matching task should have been found above";

        Task editedTask = taskToEdit.getClone(); // returns a defensive copy

        boolean isChanged = editedTask.markAsDone();

        assert (editedTask != taskToEdit) : "deep copied the task";

        if (!isChanged) {
            throw new CommandException(String.format(MESSAGE_TASK_ALREADY_DONE, moduleCode,
                    taskNum));
        }

        assert (isChanged) : "Marking the task as done successfully";

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask),
                CommandType.TASK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskMarkAsDoneCommand // instanceof handles nulls
                && this.moduleCode.equals(((TaskMarkAsDoneCommand) other).moduleCode) // state check
                && this.taskNum == (((TaskMarkAsDoneCommand) other).taskNum)); // state check
    }
}
