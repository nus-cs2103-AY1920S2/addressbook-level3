package seedu.address.logic.commands.task;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.TaskAction;

/**
 * Deletes a task in Mod Manager using its unique ID.
 */
public class TaskDeleteCommand extends TaskCommand {
    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_DELETE
            + ": Deletes the task identified "
            + "by the unique ID of the task found in both the general tasks list and module specific list.\n"
            + "Parameters: MOD_CODE ID_NUMBER\n"
            + "Example: " + COMMAND_GROUP_TASK + " " + COMMAND_WORD_DELETE + " CS2103T 848";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module %s does not exist in Mod Manager.";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task of module %s with id %d does not exist in Mod Manager.";
    public static final String MESSAGE_TASK_DELETE_SUCCESS = "Task deleted: %s.";

    private final ModuleCode moduleCode;
    private final int taskNum;

    public TaskDeleteCommand(ModuleCode moduleCode, int taskNum) {
        this.moduleCode = moduleCode;
        this.taskNum = taskNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasModuleCode(moduleCode.toString())) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode));
        }

        if (!TaskNumManager.doesNumExist(moduleCode, taskNum)) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND, moduleCode, taskNum));
        }

        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        List<Task> current = model.getFilteredTaskList();

        Task toDelete = current.stream().reduce(null, (x, y)
            -> y.getModuleCode().equals(moduleCode) && y.getTaskNum() == taskNum ? y : x);
        assert toDelete != null;

        TaskNumManager.removeNum(toDelete.getModuleCode(), toDelete.getTaskNum());
        model.deleteTask(toDelete);
        TaskAction deleteTaskAction = new TaskAction(toDelete, DoableActionType.DELETE);
        model.addAction(deleteTaskAction);

        return new CommandResult(String.format(MESSAGE_TASK_DELETE_SUCCESS, toDelete), CommandType.TASK);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskDeleteCommand)) {
            return false;
        }

        TaskDeleteCommand t = (TaskDeleteCommand) other;

        return this.taskNum == t.taskNum
                && this.moduleCode.equals(t.moduleCode);
    }
}
