package seedu.zerotoone.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;

/**
 * Lists all scheduled workouts in the {@code ScheduledWorkOutList} to the user.
 */
public class ListCommand extends ScheduleCommand {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all scheduled workouts";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing schedule list command");

        requireNonNull(model);
        if (model.isInSession()) {
            throw new CommandException(Command.MESSAGE_SESSION_STARTED);
        }

        model.populateSortedScheduledWorkoutList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
