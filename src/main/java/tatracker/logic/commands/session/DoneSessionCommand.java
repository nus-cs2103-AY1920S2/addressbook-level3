package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.INDEX;

import java.time.LocalDateTime;
import java.util.List;

import tatracker.commons.core.Messages;
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

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.DONE_SESSION,
            "Marks a session as done in TA-Tracker.",
            List.of(INDEX),
            List.of(),
            INDEX
    );

    public static final String MESSAGE_SUCCESS = "Session completed: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "Index does not exists";

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
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session session = lastShownList.get(index.getZeroBased());
        session.done();
        if (session.getRecurring() != 0) {

            int recurring = session.getRecurring();
            LocalDateTime startTime = session.getStartDateTime().plusWeeks(recurring);
            LocalDateTime endTime = session.getEndDateTime().plusWeeks(recurring);
            SessionType sessionType = session.getSessionType();
            String moduleCode = session.getModuleCode();
            String description = session.getDescription();

            Session newSession = new Session(startTime, endTime, sessionType,
                    recurring, moduleCode, description);

            model.addSession(newSession);
            model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
            return new CommandResult(String.format(AddSessionCommand.MESSAGE_SUCCESS, newSession), Action.DONE);
        }

        model.addDoneSession(session);
        model.updateFilteredDoneSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS, "");
        model.deleteSession(session);
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, session), Action.DONE);
    }

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
}
