package cookbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import cookbuddy.model.Model;

/**
 * Counts all recipes in the recipe book and displays the total number to the user.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";

    public static final String MESSAGE_SUCCESS = "Total available recipes are: ";

    private static long total;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        total = model.count();
        return new CommandResult(MESSAGE_SUCCESS + total);
    }
}
