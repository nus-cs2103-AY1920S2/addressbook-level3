package fithelper.logic.weight;

import static fithelper.logic.commands.CommandResult.DisplayedPage.WEIGHT;
import static java.util.Objects.requireNonNull;

import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class WeightCommand extends Command {

    public static final String COMMAND_WORD = "weight";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the user's weight records. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Weight Records Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS), WEIGHT, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightCommand);
    }
}
