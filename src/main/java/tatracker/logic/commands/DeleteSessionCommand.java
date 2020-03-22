package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.commands.CommandWords.DELETE_MODEL;
import static tatracker.logic.commands.CommandWords.SESSION;
import static tatracker.logic.parser.CliSyntax.PREFIX_DATE;
import static tatracker.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NOTES;
import static tatracker.logic.parser.CliSyntax.PREFIX_RECUR;
import static tatracker.logic.parser.CliSyntax.PREFIX_SESSION_TYPE;
import static tatracker.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.session.Session;


/**
 * Deletes a session identified using it's index.
 */
public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = SESSION + " " + DELETE_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session identified by its index.\n"
            + "Parameters: "
            + "index"
            + "[" + PREFIX_STARTTIME + "START] "
            + "[" + PREFIX_ENDTIME + "END] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_RECUR + "] "
            + "[" + PREFIX_MODULE + "MODULE] "
            + "[" + PREFIX_SESSION_TYPE + "SESSION_TYPE] "
            + "[" + PREFIX_NOTES + "NOTES] "
            + "Example: " + COMMAND_WORD + " 2" + PREFIX_DATE + "20-02-2020 ";

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
