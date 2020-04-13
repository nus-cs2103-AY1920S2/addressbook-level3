package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import nasa.model.Model;
import nasa.model.activity.Deadline;

/**
 * Lists all modules and their activity lists to the user.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_SUCCESS = "These are the statistics.";

    private final Predicate<Deadline> deadlinePredicate;

    public StatisticsCommand(Predicate<Deadline> deadlinePredicate) {
        this.deadlinePredicate = deadlinePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredD(deadlinePredicate);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true, false,
                CommandResult.EMPTY_BYTE_ARRAY_DATA);
    }
}
