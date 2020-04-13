// @@author Eclmist

package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_TIMES;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.EDIT_MODEL,
            "Edits a session at the shown list index",
            List.of(INDEX),
            List.of(MODULE, START_TIME, END_TIME, DATE, RECUR, SESSION_TYPE, NOTES),
            MODULE, START_TIME, END_TIME, DATE, SESSION_TYPE, NOTES
    );

    // @@author Eclmist

    public static final String MESSAGE_EDITED_SESSION_SUCCESS = "Edited session: %s";

    private final Index index;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param index                 of the session in the filtered session list to edit
     * @param editSessionDescriptor details to edit the session with
     */
    public EditSessionCommand(Index index, EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(index);
        requireNonNull(editSessionDescriptor);

        this.index = index;
        this.editSessionDescriptor = new EditSessionDescriptor(editSessionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownList = model.getFilteredSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToEdit = lastShownList.get(index.getZeroBased());
        Session editedSession = createEditedSession(sessionToEdit, editSessionDescriptor);

        if (!model.hasModule(editedSession.getModuleCode())) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (editedSession.getStartDateTime().isAfter(editedSession.getEndDateTime())) {
            throw new CommandException(MESSAGE_INVALID_SESSION_TIMES);
        }

        model.setSession(sessionToEdit, editedSession);
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_EDITED_SESSION_SUCCESS, editedSession.getMinimalDescription()),
                Action.GOTO_SESSION);
    }

    // @@author potatocombat

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editSessionDescriptor}.
     */
    private static Session createEditedSession(Session sessionToEdit, EditSessionDescriptor editSessionDescriptor) {
        assert sessionToEdit != null;

        LocalDate parsedDate = editSessionDescriptor.getDate().orElse(sessionToEdit.getDate());

        LocalTime parsedStartTime = editSessionDescriptor.getStartTime().orElse(
                sessionToEdit.getStartDateTime().toLocalTime());

        LocalTime parsedEndTime = editSessionDescriptor.getEndTime().orElse(
                sessionToEdit.getEndDateTime().toLocalTime());

        // If the date is not specified, the time arguments change the timing for the current day.
        LocalDateTime startDateTime = LocalDateTime.of(parsedDate, parsedStartTime);
        LocalDateTime endDateTime = LocalDateTime.of(parsedDate, parsedEndTime);

        int isRecurring = editSessionDescriptor.getRecurring().orElse(sessionToEdit.getRecurring());
        String moduleCode = editSessionDescriptor.getModuleCode().orElse(sessionToEdit.getModuleCode());
        SessionType type = editSessionDescriptor.getSessionType().orElse(sessionToEdit.getSessionType());
        String description = editSessionDescriptor.getDescription().orElse(sessionToEdit.getDescription());

        return new Session(startDateTime, endDateTime, type, isRecurring, moduleCode, description);
    }

    // @@author Eclmist

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

    // @@author Eclmist

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditSessionDescriptor {

        private static final int NO_RECURRING_VALUE = -1;

        private LocalDate newDate;
        private LocalTime newStartTime;
        private LocalTime newEndTime;
        private int newRecurring = NO_RECURRING_VALUE;
        private String newModuleCode;
        private SessionType newSessionType;
        private String newDescription;

        public EditSessionDescriptor() { }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setDate(toCopy.newDate);
            setStartTime(toCopy.newStartTime);
            setEndTime(toCopy.newEndTime);
            setRecurring(toCopy.newRecurring);
            setModuleCode(toCopy.newModuleCode);
            setSessionType(toCopy.newSessionType);
            setDescription(toCopy.newDescription);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            boolean otherVariables = CollectionUtil.isAnyNonNull(
                    newDate,
                    newStartTime,
                    newEndTime,
                    newModuleCode,
                    newDescription,
                    newSessionType);
            return hasRecurring() || otherVariables;
        }

        // @@author potatocombat

        public void setDate(LocalDate date) {
            this.newDate = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(newDate);
        }

        public void setStartTime(LocalTime startTime) {
            this.newStartTime = startTime;
        }

        public Optional<LocalTime> getStartTime() {
            return Optional.ofNullable(newStartTime);
        }

        public void setEndTime(LocalTime endTime) {
            this.newEndTime = endTime;
        }

        public Optional<LocalTime> getEndTime() {
            return Optional.ofNullable(newEndTime);
        }

        // @@author Eclmist

        public void setRecurring(int isRecurring) {
            this.newRecurring = isRecurring;
        }

        public Optional<Integer> getRecurring() {
            if (!hasRecurring()) {
                return Optional.empty();
            } else {
                return Optional.of(newRecurring);
            }
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

        private boolean hasRecurring() {
            return newRecurring >= 0;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSessionDescriptor)) {
                return false;
            }

            // state check
            EditSessionDescriptor e = (EditSessionDescriptor) other;

            return getDate().equals(e.getDate())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getModuleCode().equals(e.getModuleCode())
                    && getSessionType().equals(e.getSessionType())
                    && getRecurring() == e.getRecurring()
                    && getDescription().equals(e.getDescription());
        }
    }
}
