package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.model.util.Description;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.TaskAction;

/**
 * Represents a command that edits an existing task in Mod Manager.
 */
public class TaskEditCommand extends TaskCommand {
    public static final String MESSAGE_USAGE = COMMAND_GROUP_TASK + " " + COMMAND_WORD_EDIT
            + ": Edits the description and/or time of the task identified "
            + "by the unique ID of the task found in the both the general tasks list and module specific list. "
            + "Existing values will be overwritten by the input values.\n"
            + "If you simply want to remove the DateTime field, input 'non' as parameter for " + PREFIX_ON
            + " without adding " + PREFIX_AT + " parameters.\n"
            + "Parameters: MOD_CODE ID_NUMBER "
            + "[ " + PREFIX_DESCRIPTION + " DESCRIPTION] "
            + "[ " + PREFIX_ON + " DATE/non] "
            + "[ " + PREFIX_AT + " TIME]\n"
            + "Example: " + COMMAND_GROUP_TASK + " " + COMMAND_WORD_EDIT + " CS2103T 848 "
            + PREFIX_DESCRIPTION + " UG submission "
            + PREFIX_ON + " 12/04/2020 "
            + PREFIX_AT + " 23:59\n"
            + COMMAND_GROUP_TASK + " " + COMMAND_WORD_EDIT + " CS2103T 848 "
            + PREFIX_ON + " non";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Mod Manager.";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task of module %s with ID %d does not exist in Mod Manager.";
    public static final String MESSAGE_MODULE_NOT_FOUND = "The module %s does not exist in Mod Manager.";
    public static final String MESSAGE_NON_HAS_NO_TAILS = "If /on value is 'non', there shall not be an /at value.";
    public static final String SPECIAL_VALUE_NON = "non";

    private final ModuleCode moduleCode;
    private final int taskNum;
    private final EditTaskDescriptor editTaskDescriptor;
    private final Logger logger = LogsCenter.getLogger(TaskEditCommand.class);

    public TaskEditCommand(ModuleCode moduleCode, int taskNum, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(moduleCode);
        requireNonNull(editTaskDescriptor);

        this.moduleCode = moduleCode;
        this.taskNum = taskNum;
        this.editTaskDescriptor = editTaskDescriptor;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        List<Task> current = model.getFilteredTaskList();

        if (!model.hasModuleCode(moduleCode.toString())) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode.toString()));
        }

        if (!TaskNumManager.doesNumExist(moduleCode, taskNum)) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND, moduleCode.toString(), taskNum));
        }

        Task taskToEdit = current.stream().reduce(null, (x, y)
            -> y.getModuleCode().equals(moduleCode) && y.getTaskNum() == taskNum ? y : x);
        assert taskToEdit != null;

        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            logger.severe("Dups: " + editedTask.toString() + " and " + taskToEdit.toString());
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        if (taskToEdit.equals(editedTask)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        TaskAction editTaskAction = new TaskAction(taskToEdit, editedTask, DoableActionType.EDIT);
        model.addAction(editTaskAction);

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                CommandType.TASK);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code toEdit}
     * edited with {@code editTaskDescriptor}.
     * Lambdas might not be the best way to do this, but would avoid nested if-else.
     */
    private static Task createEditedTask(Task toEdit, EditTaskDescriptor editTaskDescriptor) {
        assert toEdit != null;
        assert toEdit.getTaskNum() == editTaskDescriptor.getTaskNum();
        assert toEdit.getModuleCode().equals(editTaskDescriptor.getModuleCode());

        final Description updatedDescription = editTaskDescriptor.getDescription().orElse(toEdit.getDescription());
        final Optional<TaskDateTime> updatedTaskDateTime = editTaskDescriptor.getTaskDateTime();
        final ModuleCode modCode = toEdit.getModuleCode();
        final int taskId = toEdit.getTaskNum();
        final boolean isDone = toEdit.isTaskDone();
        if (updatedTaskDateTime.isPresent()) {
            TaskDateTime value = updatedTaskDateTime.get();

            return value.equals(Task.TABOO_DATE_TIME)
                    ? Task.makeNonScheduledTask(updatedDescription, modCode, taskId, isDone)
                    : Task.makeScheduledTask(updatedDescription, value,
                    toEdit.getModuleCode(), toEdit.getTaskNum(), isDone);
        } else {
            final Task[] toReturn = new Task[1];
            toEdit.getTaskDateTime()
                    .ifPresentOrElse(taskDateTime
                        -> toReturn[0] = Task.makeScheduledTask(updatedDescription, taskDateTime,
                            modCode, taskId, isDone), ()
                        -> toReturn[0] = Task.makeNonScheduledTask(updatedDescription,
                                modCode, taskId, isDone)
                );

            return toReturn[0];
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TaskEditCommand)) {
            return false;
        }

        TaskEditCommand tec = (TaskEditCommand) other;

        return moduleCode.equals(tec.moduleCode)
                && taskNum == tec.taskNum
                && editTaskDescriptor.equals(tec.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private ModuleCode moduleCode;
        private int taskNum;
        private Description description;
        private TaskDateTime taskDateTime;
        private boolean isDone;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.moduleCode = toCopy.moduleCode;
            this.taskNum = toCopy.taskNum;
            this.description = toCopy.description;
            this.taskDateTime = toCopy.taskDateTime;
            this.isDone = toCopy.isDone;
        }

        public EditTaskDescriptor(Task task) {
            this.moduleCode = task.getModuleCode();
            this.taskNum = task.getTaskNum();
            this.description = task.getDescription();
            Optional<TaskDateTime> tdt = task.getTaskDateTime();
            if (tdt.isEmpty()) {
                this.taskDateTime = null;
            } else {
                this.taskDateTime = tdt.get();
            }
            this.isDone = task.isTaskDone();
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public ModuleCode getModuleCode() {
            return this.moduleCode;
        }

        public void setTaskNum(int taskNum) {
            this.taskNum = taskNum;
        }

        public int getTaskNum() {
            return this.taskNum;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(this.description);
        }

        public void setTaskDateTime(TaskDateTime taskDateTime) {
            this.taskDateTime = taskDateTime;
        }

        public Optional<TaskDateTime> getTaskDateTime() {
            return Optional.ofNullable(this.taskDateTime);
        }

        /**
         * Makes the task.
         */
        public Task build() {
            if (taskDateTime == null) {
                return Task.makeNonScheduledTask(description, moduleCode, taskNum, isDone);
            } else {
                return Task.makeScheduledTask(description, taskDateTime, moduleCode, taskNum, isDone);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return this.moduleCode.equals(e.moduleCode)
                    && this.taskNum == e.taskNum
                    && Optional.ofNullable(this.description).equals(Optional.ofNullable(e.description))
                    && Optional.ofNullable(this.taskDateTime).equals(Optional.ofNullable(e.taskDateTime))
                    && this.isDone == (e.isDone);
        }
    }
}
