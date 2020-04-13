package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.List;
import java.util.Optional;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.commons.util.CollectionUtil;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

/* @@author don-tay */
/**
 * Edits a specific deadline in the moduleCode's list.
 */
public class EditDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "edit-d";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the deadline identified "
            + "by the index number used in the displayed moduleCode's deadline list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_MODULE + "MODULE CODE "
            + "[" + PREFIX_DATE + "DUE DATE] "
            + "[" + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_MODULE + "CS2030 "
            + PREFIX_DATE + "20-05-2020 23:59 "
            + PREFIX_ACTIVITY_NAME + "Assignment 2.3";

    public static final String MESSAGE_EDIT_DEADLINE_SUCCESS = "Edited Deadline successfully.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_NO_NEW_EDIT = "No new field is being edited.";
    public static final String MESSAGE_NO_PAST_DEADLINE = "Cannot edit deadline to a date that has passed.";

    private final Index index;
    private final ModuleCode moduleCode;
    private final EditDeadlineCommand.EditDeadlineDescriptor editDeadlineDescriptor;

    /**
     * Creates an EditDeadlineCommand to edit a deadline
     * with the specified {@code index} from the specified {@code moduleCode} list.
     * @param index index of the deadline item as specified in the corresponding module
     * @param moduleCode module code which the deadline item is found in
     * @param editDeadlineDescriptor
     */
    public EditDeadlineCommand(Index index, ModuleCode moduleCode, EditDeadlineDescriptor editDeadlineDescriptor) {
        requireAllNonNull(index, moduleCode, editDeadlineDescriptor);
        this.index = index;
        this.moduleCode = moduleCode;
        this.editDeadlineDescriptor = new EditDeadlineDescriptor(editDeadlineDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) { // throw exception if module code is not found in nasa book
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        List<Deadline> lastShownList = model.getFilteredDeadlineList(moduleCode);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Deadline deadlineToEdit = lastShownList.get(index.getZeroBased());

        requireNonNull(deadlineToEdit);

        Deadline editedDeadline = createEditedDeadline(deadlineToEdit, editDeadlineDescriptor);

        // check due date validity only when due date is edited
        if (isDueDateEdited() && !editedDeadline.isValidDeadline(editedDeadline.getDueDate())) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_NO_PAST_DEADLINE);
        }

        // case when edit made to deadline is exactly the same
        if (deadlineToEdit.isSameDeadline(editedDeadline)) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_NO_NEW_EDIT);
        }

        editedDeadline.setSchedule(deadlineToEdit.getSchedule());
        model.setDeadline(moduleCode, deadlineToEdit, editedDeadline);

        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);

        return new CommandResult(String.format(MESSAGE_EDIT_DEADLINE_SUCCESS, editedDeadline));
    }

    /**
     * Creates and returns an {@code Deadline} with the details of {@code deadlineToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Deadline createEditedDeadline(Deadline deadlineToEdit,
                                                 EditDeadlineDescriptor editDeadlineDescriptor) {
        requireNonNull(deadlineToEdit);
        Name updatedName = editDeadlineDescriptor.getName().orElse(deadlineToEdit.getName());
        // by default date created cannot be edited, and will take previous value
        Date updatedDateCreated = editDeadlineDescriptor.getDateCreated().orElse(deadlineToEdit.getDateCreated());
        Note updatedNote = editDeadlineDescriptor.getNote().orElse(deadlineToEdit.getNote());
        Priority updatedPriority = editDeadlineDescriptor.getPriority().orElse(deadlineToEdit.getPriority());
        Date updatedDueDate = editDeadlineDescriptor.getDueDate().orElse(deadlineToEdit.getDueDate());

        return new Deadline(updatedName, updatedDateCreated, updatedNote, updatedPriority, updatedDueDate,
                deadlineToEdit.isDone());

    }

    /**
     * Checks if due date has been edited and returns true. Else, false.
     * @return true if {@code editDeadlineDescriptor} is updated with new due date. Else, return false.
     */
    private boolean isDueDateEdited() {
        return this.editDeadlineDescriptor.getDueDate().isPresent();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditDeadlineCommand // instanceof handles nulls
                && index.equals(((EditDeadlineCommand) other).index)
                && moduleCode.equals(((EditDeadlineCommand) other).moduleCode)
                && editDeadlineDescriptor.equals(((EditDeadlineCommand) other).editDeadlineDescriptor));
    }

    /**
     * Stores the details to edit the deadline with. Each non-empty field value will replace the
     * corresponding field value of the moduleCode.
     */
    public static class EditDeadlineDescriptor {
        private Name name;
        private Date dateCreated;
        private Note note;
        private Priority priority;
        private Date dueDate;

        public EditDeadlineDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditDeadlineDescriptor(EditDeadlineCommand.EditDeadlineDescriptor toCopy) {
            setName(toCopy.name);
            setDateCreated(toCopy.dateCreated);
            setNote(toCopy.note);
            setPriority(toCopy.priority);
            setDueDate(toCopy.dueDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, dateCreated, note, priority, dueDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Optional<Date> getDateCreated() {
            return Optional.ofNullable(dateCreated);
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        public Optional<Date> getDueDate() {
            return Optional.ofNullable(dueDate);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDeadlineCommand.EditDeadlineDescriptor)) {
                return false;
            }

            // state check
            EditDeadlineCommand.EditDeadlineDescriptor e = (EditDeadlineCommand.EditDeadlineDescriptor) other;

            return getName().equals(e.getName())
                    && getDateCreated().equals(e.getDateCreated())
                    && getNote().equals(e.getNote())
                    && getPriority().equals(e.getPriority())
                    && getDueDate().equals(e.getDueDate());
        }
    }
}
