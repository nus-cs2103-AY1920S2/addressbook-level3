package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_LOGS;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;

/**
 * Lists all sessions in the log list to the user.
 */
public class ListCommand extends LogCommand {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all logs";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing log list command.");

        if (model.isInSession()) {
            throw new CommandException(Command.MESSAGE_SESSION_STARTED);
        }

        model.updateFilteredLogList(PREDICATE_SHOW_ALL_LOGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListCommand;
    }
}
