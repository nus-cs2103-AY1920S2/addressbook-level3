//@@author chuayijing
package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_TIMES;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.END_TIME;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NOTES;
import static tatracker.logic.parser.Prefixes.RECUR;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;
import static tatracker.logic.parser.Prefixes.START_TIME;

import java.time.LocalDateTime;
import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.session.Session;

/**
 * Adds a session to the TATracker.
 */
public class AddSessionCommand extends Command {

    //@@author potatoCombat
    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.ADD_MODEL,
            "Adds a session into TA-Tracker",
            List.of(MODULE),
            List.of(START_TIME, END_TIME, DATE, RECUR, SESSION_TYPE, NOTES),
            MODULE, START_TIME, END_TIME, DATE, SESSION_TYPE, NOTES
    );

    //@@author Chuayijing
    public static final String MESSAGE_ADD_SESSION_SUCCESS = "New session added: %s";

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

        LocalDateTime start = toAdd.getStartDateTime();
        LocalDateTime end = toAdd.getEndDateTime();

        if (start.isAfter(end)) {
            throw new CommandException(MESSAGE_INVALID_SESSION_TIMES);
        }

        if (!model.hasModule(toAdd.getModuleCode())) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        // if (model.hasSession(toAdd)) {
        //     throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        // }

        model.addSession(toAdd);
        return new CommandResult(
                String.format(MESSAGE_ADD_SESSION_SUCCESS, toAdd.getMinimalDescription()),
                Action.GOTO_SESSION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSessionCommand // instanceof handles nulls
                && toAdd.equals(((AddSessionCommand) other).toAdd));
    }
}
