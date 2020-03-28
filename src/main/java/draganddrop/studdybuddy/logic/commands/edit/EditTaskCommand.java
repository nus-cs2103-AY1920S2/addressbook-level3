package draganddrop.studdybuddy.logic.commands.edit;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;

import draganddrop.studdybuddy.commons.core.Messages;
import draganddrop.studdybuddy.commons.core.index.Index;
import draganddrop.studdybuddy.logic.commands.Command;
import draganddrop.studdybuddy.logic.commands.CommandResult;
import draganddrop.studdybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studdybuddy.model.Model;
import draganddrop.studdybuddy.model.module.Module;
import draganddrop.studdybuddy.model.module.exceptions.ModuleCodeException;
import draganddrop.studdybuddy.model.task.Task;
import draganddrop.studdybuddy.model.task.TaskField;
import draganddrop.studdybuddy.model.task.TaskType;


/**
 * Edits the details of an existing task in the address book.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String EDIT_TASK_SUCCESS = "Task has been edited successfully";
    private final Index targetIndex;
    private final TaskField taskField;
    private String newTaskName = null;
    private TaskType newTaskType = null;
    private LocalDateTime[] newDateTimes = null;
    private Module newModule = null;

    /**
     * Creates an edit task command
     * @param targetIndex index of target task to be edited
     * @param taskField the task field to be edited
     */
    public EditTaskCommand(Index targetIndex, TaskField taskField) {
        requireNonNull(targetIndex);
        requireNonNull(taskField);
        this.targetIndex = targetIndex;
        this.taskField = taskField;
    }

    public void provideNewTaskName(String newTaskName) {
        this.newTaskName = newTaskName;
    }

    public void provideNewTaskType(TaskType newTaskType) {
        this.newTaskType = newTaskType;
    }

    public void provideNewDateTime(LocalDateTime[] newDateTimes) {
        this.newDateTimes = newDateTimes;
    }

    public void provideNewModule(Module newModule) {
        this.newModule = newModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToEdit = lastShownList.get(targetIndex.getZeroBased());
        switch (taskField) {
        case TASK_NAME:
            requireNonNull(newTaskName);
            model.setTaskName(taskToEdit, newTaskName);
            break;
        case TASK_TYPE:
            requireNonNull(newTaskType);
            model.setTaskType(taskToEdit, newTaskType);
            break;
        case TASK_DATETIME:
            requireNonNull(newTaskType);
            model.setTaskDateTime(taskToEdit, newDateTimes);
            break;
        case TASK_MODULE:
            requireNonNull(newModule);
            try {
                model.setTaskMod(taskToEdit, newModule);
            } catch (ModuleCodeException ex) {
                throw new CommandException("module code invalid/does not exist!!!");
            }
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + taskField);
        }
        return new CommandResult(EDIT_TASK_SUCCESS);
    }
}
