package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_DATE;
import static tatracker.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static tatracker.logic.parser.CliSyntax.PREFIX_MOD_CODE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NOTES;
import static tatracker.logic.parser.CliSyntax.PREFIX_RECUR;
import static tatracker.logic.parser.CliSyntax.PREFIX_SESSION_TYPE;
import static tatracker.logic.parser.CliSyntax.PREFIX_STARTTIME;

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
            + "[" + PREFIX_STARTTIME + "START] "
            + "[" + PREFIX_ENDTIME + "END] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_RECUR + "RECURS] "
            + "[" + PREFIX_MOD_CODE + "MOD_CODE] "
            + "[" + PREFIX_SESSION_TYPE + "SESSION_TYPE] "
            + "[" + PREFIX_NOTES + "NOTES] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STARTTIME + "14:00 "
            + PREFIX_ENDTIME + "16:00 "
            + PREFIX_DATE + "19-02-2020 "
            + PREFIX_MOD_CODE + "CS2103T "
            + PREFIX_SESSION_TYPE + "tutorial "
            + PREFIX_NOTES + "Location: PLAB 04";

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
