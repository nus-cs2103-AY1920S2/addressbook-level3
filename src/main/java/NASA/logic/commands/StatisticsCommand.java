package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import nasa.model.Model;

/**
 * Lists all modules and their activity lists to the user.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_SUCCESS = "These are the statistics for %s." ;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
