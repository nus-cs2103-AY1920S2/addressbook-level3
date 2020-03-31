package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.WEIGHT;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.LogsCenter;
import fithelper.model.Model;

import java.util.logging.Logger;

/**
 * Adds a entry to FitHelper.
 */
public class WeightCommand extends Command {

    public static final String COMMAND_WORD = "weight";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the user's weight records. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Weight Records Page ~";

    private static final String MESSAGE_COMMIT = "Switch to weight record page";

    private static final Logger logger = LogsCenter.getLogger(WeightCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.commit(MESSAGE_COMMIT);
        logger.info("Displayed the user weight records");

        return new CommandResult(String.format(MESSAGE_SUCCESS), WEIGHT, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightCommand);
    }
}
