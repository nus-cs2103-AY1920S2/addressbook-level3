package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.Statistics;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;

/**
 * Display session statistics to user.
 */
public class DisplayCommand extends LogCommand {
    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_SUCCESS = "Displaying statistics!";
    public static final String MESSAGE_USAGE =
        "Usage: log display [st/start_time] [et/end_time]";

    private static final Logger logger = LogsCenter.getLogger(Statistics.class);

    private final Optional<LocalDateTime> startRange;
    private final Optional<LocalDateTime> endRage;


    public DisplayCommand(Optional<LocalDateTime> startRange, Optional<LocalDateTime> endRange) {
        this.startRange = startRange;
        this.endRage = endRange;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.isInSession()) {
            throw new CommandException(Command.MESSAGE_SESSION_STARTED);
        }

        model.setStatisticsDateRange(startRange, endRage);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DisplayCommand;
    }
}
