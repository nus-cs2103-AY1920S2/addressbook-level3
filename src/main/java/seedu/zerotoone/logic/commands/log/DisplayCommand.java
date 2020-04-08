package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;

/**
 * Display session statistics to user.
 */
public class DisplayCommand extends LogCommand {
    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_SUCCESS = "Displaying statistics!";

    private final Optional<LocalDateTime> startRange;
    private final Optional<LocalDateTime> endRage;


    public DisplayCommand(Optional<LocalDateTime> startRange, Optional<LocalDateTime> endRange) {
        this.startRange = startRange;
        this.endRage = endRange;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setStatisticsDateRange(startRange, endRage);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
