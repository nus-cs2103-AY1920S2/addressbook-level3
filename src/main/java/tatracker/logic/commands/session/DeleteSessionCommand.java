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

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.model.Model;
import tatracker.model.session.Session;


/**
 * Deletes a session identified using it's index.
 */
public class DeleteSessionCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.DELETE_MODEL,
            "Deletes the session identified by its index.",
            List.of(INDEX),
            List.of(),
            INDEX
    );

    public static final String COMMAND_WORD = CommandWords.SESSION + " " + CommandWords.DELETE_MODEL;

    public static final List<Prefix> PARAMETERS = List.of(INDEX);

    public static final String INFO = "Deletes the session identified by its index.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session identified by its index.\n"
            + "Parameters: "
            + "index"
            + "[" + START_TIME + "START] "
            + "[" + END_TIME + "END] "
            + "[" + DATE + "DATE] "
            + "[" + RECUR + "] "
            + "[" + MODULE + "MODULE] "
            + "[" + SESSION_TYPE + "SESSION_TYPE] "
            + "[" + NOTES + "NOTES] "
            + "Example: " + COMMAND_WORD + " 2" + DATE + "20-02-2020 ";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted Session: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "Index does not exists";

    private final Index index;

    public DeleteSessionCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Session> lastShownList = model.getFilteredSessionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToDelete = lastShownList.get(index.getZeroBased());
        model.deleteSession(sessionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSessionCommand // instanceof handles nulls
                && index.equals(((DeleteSessionCommand) other).index)); // state check
    }
}
