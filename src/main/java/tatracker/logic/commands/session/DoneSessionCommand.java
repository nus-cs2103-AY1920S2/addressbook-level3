// @@author Eclmist
package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX;
import static tatracker.logic.parser.Prefixes.INDEX;

import java.time.LocalDateTime;
import java.util.List;

import tatracker.commons.core.index.Index;
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
 * Marks a session as done in TAT.
 */
public class DoneSessionCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.DONE_SESSION,
            "Marks the session at the shown list index as done",
            List.of(INDEX),
            List.of(),
            INDEX
    );

    // @@author Eclmist

    public static final String MESSAGE_DONE_SESSION_SUCCESS = "Session completed: %s";
    public static final String MESSAGE_REPEAT_SESSION_SUCCESS = "\nRepeating session: %s - %s";

    private final Index index;

    /**
     * @param index of the session in the filtered session list to edit
     */
    public DoneSessionCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownList = model.getFilteredSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session session = lastShownList.get(index.getZeroBased());
        session.done();

        // @@author Chuayijing
        if (session.getRecurring() > 0) {

            int recurring = session.getRecurring();
            LocalDateTime startTime = session.getStartDateTime().plusWeeks(recurring);
            LocalDateTime endTime = session.getEndDateTime().plusWeeks(recurring);
            SessionType sessionType = session.getSessionType();
            String moduleCode = session.getModuleCode();
            String description = session.getDescription();

            Session newSession = new Session(startTime, endTime, sessionType,
                    recurring, moduleCode, description);

            model.addSession(newSession);
            model.deleteSession(session);
            model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
            model.addDoneSession(session);
            model.updateFilteredDoneSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS, "");
            return new CommandResult(getRepeatMessage(newSession), Action.DONE);
        }

        model.deleteSession(session);
        model.addDoneSession(session);
        model.updateFilteredDoneSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS, "");

        return new CommandResult(String.format(MESSAGE_DONE_SESSION_SUCCESS, session.getMinimalDescription()),
                Action.DONE);
    }

    // @@author Eclmist

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoneSessionCommand)) {
            return false;
        }

        // state check
        DoneSessionCommand e = (DoneSessionCommand) other;
        return index.equals(e.index);
    }

    // @@author potatocombat

    private String getRepeatMessage(Session session) {
        String doneMessage = String.format(MESSAGE_DONE_SESSION_SUCCESS, session.getMinimalDescription());
        String repeatMessage = String.format(MESSAGE_REPEAT_SESSION_SUCCESS,
                session.getStartDateTimeDescription(),
                session.getEndDateTimeDescription());

        return doneMessage + repeatMessage;
    }
}
