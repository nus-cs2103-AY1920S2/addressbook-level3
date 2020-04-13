package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;
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
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

/* @@author don-tay */
/**
 * Edits a specific event in the moduleCode's list.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed moduleCode's event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_MODULE + "MODULE CODE "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_MODULE + "CS2030 "
            + PREFIX_START_DATE + "20-05-2020 23:59 "
            + PREFIX_END_DATE + "20-05-2020 23:59 "
            + PREFIX_ACTIVITY_NAME + "Assignment 2.3";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event successfully.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_NO_NEW_EDIT = "No new field is being edited.";
    public static final String MESSAGE_NO_PAST_EVENT = "Cannot edit event to an end date that has passed.";
    public static final String MESSAGE_INVALID_DATE = "Cannot edit event to have end date before start date.";

    private final Index index;
    private final ModuleCode moduleCode;
    private final EditEventCommand.EditEventDescriptor editEventDescriptor;

    /**
     * Creates an EditEventCommand to edit a event
     * with the specified {@code index} from the specified {@code moduleCode} list.
     * @param index index of the event item as specified in the corresponding module
     * @param moduleCode module code which the event item is found in
     * @param editEventDescriptor
     */
    public EditEventCommand(Index index, ModuleCode moduleCode, EditEventDescriptor editEventDescriptor) {
        requireAllNonNull(index, moduleCode, editEventDescriptor);
        this.index = index;
        this.moduleCode = moduleCode;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) { // throw exception if module code is not found in nasa book
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        List<Event> lastShownList = model.getFilteredEventList(moduleCode);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());

        requireNonNull(eventToEdit);

        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!editedEvent.isValidStartEndDates(editedEvent.getStartDate(), editedEvent.getEndDate())) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_INVALID_DATE);
        }

        // check end date validity only when end date is edited
        if (isEndDateEdited() && !editedEvent.isValidFutureEvent(editedEvent.getEndDate())) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_NO_PAST_EVENT);
        }

        if (eventToEdit.isSameEvent(editedEvent)) { // if edit is exactly the same as the original
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_NO_NEW_EDIT);
        }

        editedEvent.setSchedule(eventToEdit.getSchedule());
        model.setEvent(moduleCode, eventToEdit, editedEvent);

        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);

        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));

    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        requireNonNull(eventToEdit);

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        Date updatedDateCreated = editEventDescriptor.getDateCreated().orElse(eventToEdit.getDateCreated());
        Note updatedNote = editEventDescriptor.getNote().orElse(eventToEdit.getNote());
        Date updatedStartDate = editEventDescriptor.getStartDate().orElse(eventToEdit.getStartDate());
        Date updatedEndDate = editEventDescriptor.getEndDate().orElse(eventToEdit.getEndDate());

        return new Event(updatedName, updatedDateCreated, updatedNote, updatedStartDate, updatedEndDate);

    }

    /**
     * Checks if end date has been edited and returns true. Else, false.
     * @return true if {@code editEventDescriptor} is updated with new end date. Else, return false.
     */
    private boolean isEndDateEdited() {
        return this.editEventDescriptor.getEndDate().isPresent();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditEventCommand // instanceof handles nulls
                && index.equals(((EditEventCommand) other).index)
                && moduleCode.equals(((EditEventCommand) other).moduleCode)
                && editEventDescriptor.equals(((EditEventCommand) other).editEventDescriptor));
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the moduleCode.
     */
    public static class EditEventDescriptor {
        private Name name;
        private Date dateCreated;
        private Note note;
        private Priority priority;
        private Date startDate;
        private Date endDate;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEventDescriptor(EditEventCommand.EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setDateCreated(toCopy.dateCreated);
            setNote(toCopy.note);
            setPriority(toCopy.priority);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, dateCreated, note, priority, startDate, endDate);
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

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Optional<Date> getEndDate() {
            return Optional.ofNullable(endDate);
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
            if (!(other instanceof EditEventCommand.EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventCommand.EditEventDescriptor e = (EditEventCommand.EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getDateCreated().equals(e.getDateCreated())
                    && getNote().equals(e.getNote())
                    && getPriority().equals(e.getPriority())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate());
        }
    }
}
