package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.END_TIME;
import static tatracker.logic.parser.Prefixes.INDEX;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NOTES;
import static tatracker.logic.parser.Prefixes.RECUR;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;
import static tatracker.logic.parser.Prefixes.START_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.commons.util.CollectionUtil;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;


/**
 * Edits the details of an existing session in TAT.
 */
public class EditSessionCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.EDIT_MODEL,
            "Edits a session at the shown list index.",
            List.of(INDEX),
            List.of(MODULE, START_TIME, END_TIME, DATE, RECUR, SESSION_TYPE, NOTES),
            MODULE, START_TIME, END_TIME, DATE, SESSION_TYPE, NOTES
    );

    public static final String MESSAGE_SUCCESS = "Session updated: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditSessionCommand.EditSessionDescriptor editSessionDescriptor;

    /**
     * @param index                 of the session in the filtered session list to edit
     * @param editSessionDescriptor details to edit the session with
     */
    public EditSessionCommand(Index index, EditSessionCommand.EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(index);
        requireNonNull(editSessionDescriptor);

        this.index = index;
        this.editSessionDescriptor = new EditSessionCommand.EditSessionDescriptor(editSessionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownList = model.getFilteredSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToEdit = lastShownList.get(index.getZeroBased());
        Session editedSession = createEditedSession(sessionToEdit, editSessionDescriptor);

        // Session does not have an unique identifier like students/modules, and as such checking if
        // the two objects are the same should not be done based on their field values.
        // In this case, it is probably best to ignore no-edits.
        /*
        if (!sessionToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        */

        model.setSession(sessionToEdit, editedSession);
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedSession), Action.GOTO_SESSION);
    }

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editSessionDescriptor}.
     */
    private static Session createEditedSession(Session sessionToEdit,
                                               EditSessionCommand.EditSessionDescriptor editSessionDescriptor) {
        assert sessionToEdit != null;

        LocalDateTime startTime = editSessionDescriptor.getStartTime().orElse(sessionToEdit.getStartDateTime());
        LocalDateTime endTime = editSessionDescriptor.getEndTime().orElse(sessionToEdit.getEndDateTime());
        int isRecurring = editSessionDescriptor.getRecurring();
        String moduleCode = editSessionDescriptor.getModuleCode().orElse(sessionToEdit.getModuleCode());
        SessionType type = editSessionDescriptor.getSessionType().orElse(sessionToEdit.getSessionType());
        String description = editSessionDescriptor.getDescription().orElse(sessionToEdit.getDescription());

        // If date is not updated, reset the date to the original date.
        // We need to do this because EditSessionCommandParser is not able to know that is the original date
        // to use as default value, so an arbitrary default date is used.
        if (!editSessionDescriptor.getIsDateChanged()) {
            LocalDate originalDate = sessionToEdit.getDate();
            startTime = LocalDateTime.of(originalDate.getYear(), originalDate.getMonth(), originalDate.getDayOfMonth(),
                    startTime.getHour(), startTime.getMinute(), startTime.getSecond());
            endTime = LocalDateTime.of(originalDate.getYear(), originalDate.getMonth(), originalDate.getDayOfMonth(),
                    endTime.getHour(), endTime.getMinute(), endTime.getSecond());
        }

        return new Session(startTime, endTime, type, isRecurring, moduleCode, description);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSessionCommand)) {
            return false;
        }

        // state check
        EditSessionCommand e = (EditSessionCommand) other;
        return index.equals(e.index)
                && editSessionDescriptor.equals(e.editSessionDescriptor);
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditSessionDescriptor {
        private LocalDateTime newStartTime;
        private LocalDateTime newEndTime;
        private boolean isDateChanged;
        private int isRecurring;
        private String newModuleCode;
        private SessionType newSessionType;
        private String newDescription;

        public EditSessionDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setStartTime(toCopy.newStartTime);
            setEndTime(toCopy.newEndTime);
            setRecurring(toCopy.isRecurring);
            setModuleCode(toCopy.newModuleCode);
            setSessionType(toCopy.newSessionType);
            setDescription(toCopy.newDescription);
            this.isDateChanged = toCopy.isDateChanged;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return isDateChanged || CollectionUtil.isAnyNonNull(newStartTime, newEndTime,
                    newModuleCode, newDescription, newSessionType, isRecurring);
        }

        public void setStartTime(LocalDateTime startTime) {
            this.newStartTime = startTime;
        }

        public Optional<LocalDateTime> getStartTime() {
            return Optional.ofNullable(newStartTime);
        }

        public void setEndTime(LocalDateTime endTime) {
            this.newEndTime = endTime;
        }

        public Optional<LocalDateTime> getEndTime() {
            return Optional.ofNullable(newEndTime);
        }

        public void setRecurring(int isRecurring) {
            this.isRecurring = isRecurring;
        }

        public int getRecurring() {
            return this.isRecurring;
        }

        public void setIsDateChanged(boolean isChanged) {
            this.isDateChanged = isChanged;
        }

        public boolean getIsDateChanged() {
            return this.isDateChanged;
        }

        public void setModuleCode(String moduleCode) {
            this.newModuleCode = moduleCode;
        }

        public Optional<String> getModuleCode() {
            return Optional.ofNullable(newModuleCode);
        }

        public void setSessionType(SessionType sessionType) {
            this.newSessionType = sessionType;
        }

        public Optional<SessionType> getSessionType() {
            return Optional.ofNullable(newSessionType);
        }

        public void setDescription(String description) {
            this.newDescription = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(newDescription);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSessionCommand.EditSessionDescriptor)) {
                return false;
            }

            // state check
            EditSessionCommand.EditSessionDescriptor e = (EditSessionCommand.EditSessionDescriptor) other;

            return getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getSessionType().equals(e.getSessionType())
                    && getDescription().equals(e.getDescription());
        }
    }
}
