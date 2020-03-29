package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.END_TIME;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NOTES;
import static tatracker.logic.parser.Prefixes.RECUR;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;
import static tatracker.logic.parser.Prefixes.START_TIME;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.session.Session;

/**
 * Adds a session to the TATracker.
 */
public class AddSessionCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.SESSION + " " + CommandWords.ADD_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a session in TA-Tracker. "
            + "Parameters: "
            + "[" + START_TIME + "START] "
            + "[" + END_TIME + "END] "
            + "[" + DATE + "DATE] "
            + "[" + RECUR + "RECURS] "
            + "[" + MODULE + "MODULE CODE] "
            + "[" + SESSION_TYPE + "SESSION TYPE] "
            + "[" + NOTES + "NOTES] "
            + "Example: " + COMMAND_WORD + " "
            + START_TIME + "14:00 "
            + END_TIME + "16:00 "
            + DATE + "19-02-2020 "
            + MODULE + "CS2103T "
            + SESSION_TYPE + "tutorial "
            + NOTES + "Location: PLAB 04";

    public static final String MESSAGE_SUCCESS = "New session added: %1$s";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists in the TA-Tracker";

    private final Session toAdd;

    /**
     * Creates an AddSessionCommand to add the specified {@code Session}
     */
    public AddSessionCommand(Session session) {
        requireNonNull(session);
        toAdd = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSession(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.addSession(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSessionCommand // instanceof handles nulls
                && toAdd.equals(((AddSessionCommand) other).toAdd));
    }
}
