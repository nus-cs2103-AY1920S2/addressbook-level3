package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jelphabot.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.commons.util.CollectionUtil;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.TimeSpent;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
                                                   + "by the index number used in the displayed task list. "
                                                   + "Existing values will be overwritten by the input values.\n"
                                                   + "Parameters:\n"
                                                   + "    " + "INDEX (must be a positive integer)\n"
                                                   + "    " + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
                                                   + "    " + "[" + PREFIX_DATETIME + "DATETIME]\n"
                                                   + "    " + "[" + PREFIX_MODULE_CODE + "MODULE_CODE]\n"
                                                   + "    " + "[" + PREFIX_PRIORITY + "PRIORITY (-1, 0, or 1)]\n"
                                                   + "    " + "[" + PREFIX_TAG + "TAG]...\n"
                                                   + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MODULE_CODE + "CS2105 "
                                                   + PREFIX_DATETIME + "Jan-1-2020 20 20 "
                                                   + PREFIX_TAG + "Ungraded";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";
    public static final String MESSAGE_CANNOT_EDIT_TASK = "This task cannot be edited while the timer is running.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index              of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        ModuleCode updatedModuleCode = editTaskDescriptor.getModuleCode().orElse(taskToEdit.getModuleCode());
        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());
        DateTime dateTime = editTaskDescriptor.getDateTime().orElse(taskToEdit.getDateTime());
        Status updatedStatus = taskToEdit.getStatus();
        Priority updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());
        TimeSpent timeSpent = taskToEdit.getTimeSpent();

        return new Task(updatedDescription, updatedStatus, dateTime, updatedModuleCode, updatedPriority, updatedTags,
            timeSpent
        );
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewTaskList lastShownList = model.getLastShownList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (taskToEdit.isBeingTimed()) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_TASK);
        } else if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.setProductivity(new Productivity(model.getFilteredTaskList(), true, false, false));
        model.setSummary(new Summary(model.getFilteredTaskList()));
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index) && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Description description;
        private ModuleCode moduleCode;
        private Set<Tag> tags;
        private DateTime dateTime;
        private Priority priority;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setDescription(toCopy.description);
            setModuleCode(toCopy.moduleCode);
            setTags(toCopy.tags);
            setDateTime(toCopy.dateTime);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, dateTime, moduleCode, tags, priority);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of
         * {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException} if modification is attempted. Returns
         * {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getDescription().equals(e.getDescription()) && getDateTime().equals(e.getDateTime())
                       && getModuleCode().equals(e.getModuleCode())
                       && getPriority().equals(e.getPriority()) && getTags().equals(e.getTags());
        }
    }
}
