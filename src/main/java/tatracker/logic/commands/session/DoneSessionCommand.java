package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.session.Session;

/**
 * Marks a session as done in TAT.
 */
public class DoneSessionCommand extends Command {

    public static final String COMMAND_WORD_DONE = "done";
    public static final String COMMAND_WORD = String.format("%s %s", CommandWords.SESSION, COMMAND_WORD_DONE);

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a session as done in TA-Tracker. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)";

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
        model.updateFilteredSessionList(Model.PREDICATE_SHOW_ALL_SESSIONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, session));
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
