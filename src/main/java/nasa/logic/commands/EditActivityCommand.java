package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.commons.util.CollectionUtil;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.*;
import nasa.model.module.Module;

import java.util.List;
import java.util.Optional;

/**
 * Edits a specific activity in the module's list.
 */
public class EditActivityCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the displayed module's activity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE" +
            "INDEX (must be a positive integer)"
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_MODULE + "CS2030"
            + "1"
            + PREFIX_DATE + "2020-03-20"
            + PREFIX_ACTIVITY_NAME + "Assignment 2.3";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the module activity list.";

    private final Index index;
    private final Module module;
    private final EditActivityCommand.EditActivityDescriptor editActivityDescriptor;

    /**
     * Creates an EditActivityCommand to edit an activity
     * with specified {@code index} from {@code moduleCode} list.
     * @param index
     * @param module
     * @param editActivityDescriptor
     */
    public EditActivityCommand(Index index, Module module, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(index);
        this.index = index;
        this.module = module;
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new nasa.logic.commands.exceptions.CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToEdit = lastShownList.get(index.getZeroBased());
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

        requireNonNull(editedActivity);

        if (!activityToEdit.isSameActivity(editedActivity) && model.hasActivity(module, editedActivity)) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.setActivity(activityToEdit, editedActivity);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }

    /**
     * Creates and returns an {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                  EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        Name updatedName = editActivityDescriptor.getName().orElse(activityToEdit.getName());
        Date updatedDate = editActivityDescriptor.getDate().orElse(activityToEdit.getDate());
        Note updatedNote = editActivityDescriptor.getNote().orElse(activityToEdit.getNote());
        Priority updatedPriority = editActivityDescriptor.getPriority().orElse(activityToEdit.getPriority());
        Status status = activityToEdit.getStatus(); // original module's activity list is preserved

        if (activityToEdit instanceof Deadline) {
            Date dueDate = ((Deadline) activityToEdit).getDueDate();
            return new Deadline(updatedName, updatedDate, updatedNote, status, updatedPriority, dueDate);
        } else if (activityToEdit instanceof Event) {
            Date startDate = ((Event) activityToEdit).getDateFrom();
            Date endDate = ((Event) activityToEdit).getDateTo();
            return new Event(updatedName, updatedDate, updatedNote, status, updatedPriority,
                    startDate, endDate);
        } else if (activityToEdit instanceof Lesson) {
            Date startDate = ((Lesson) activityToEdit).getDateFrom();
            Date endDate = ((Lesson) activityToEdit).getDateTo();
            return new Event(updatedName, updatedDate, updatedNote, status, updatedPriority,
                    startDate, endDate);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditActivityCommand // instanceof handles nulls
                && index.equals(((EditActivityCommand) other).index));
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditActivityDescriptor {
        private Name name;
        private Date date;
        private Note note;
        private Priority priority;

        public EditActivityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditActivityDescriptor(EditActivityCommand.EditActivityDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setNote(toCopy.note);
            setPriority(toCopy.priority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, note, priority);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
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
            if (!(other instanceof EditActivityCommand.EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityCommand.EditActivityDescriptor e = (EditActivityCommand.EditActivityDescriptor) other;

            return getName().equals(e.getName()) && getDate().equals(e.getDate()) && getNote().equals(e.getNote())
                    && getPriority().equals(e.getPriority());
        }
    }
}
