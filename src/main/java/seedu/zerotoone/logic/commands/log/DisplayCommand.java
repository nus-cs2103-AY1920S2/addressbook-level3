package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * Display session statistics to user.
 */
public class DisplayCommand extends LogCommand {
    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_SUCCESS = "Displaying report!";

    private final Optional<LocalDateTime> startRange;
    private final Optional<LocalDateTime> endRage;


    public DisplayCommand(Optional<LocalDateTime> startRange, Optional<LocalDateTime> endRange) {
        this.startRange = startRange;
        this.endRage = endRange;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.initStatisticsLogList(startRange, endRage);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
