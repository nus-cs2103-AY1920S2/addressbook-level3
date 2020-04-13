//@@author chuayijing

package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX;
import static tatracker.logic.parser.Prefixes.INDEX;

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

/**
 * Deletes a session identified using it's index.
 */
public class DeleteSessionCommand extends Command {

    //@@author potatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.DELETE_MODEL,
            "Deletes the session at the shown list index",
            List.of(INDEX),
            List.of(),
            INDEX
    );

    //@@author Chuayijing
    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted session: %s";

    private final Index index;

    public DeleteSessionCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownList = model.getFilteredSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_SESSION_DISPLAYED_INDEX));
        }

        Session sessionToDelete = lastShownList.get(index.getZeroBased());
        model.deleteSession(sessionToDelete);
        return new CommandResult(
                String.format(MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete.getMinimalDescription()),
                Action.GOTO_SESSION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSessionCommand // instanceof handles nulls
                && index.equals(((DeleteSessionCommand) other).index)); // state check
    }
}
