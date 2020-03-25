package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.model.Mode;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.self.Self;

/**
 * Sets the Mode of the Diet Tracker depending on user preferences. The 3 modes available are
 * -l for weight loss, -g for weight gain, and -m for maintaining weight.
 */
public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the mode in which you want to use the diet app "
            + "for tracking. "
            + "Parameters: [-l] [-g] [-m]";

    public static final String MESSAGE_SUCCESS = "Mode Change Successful: %1$s";

    private final Mode mode;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public ModeCommand(Mode mode) {
        requireNonNull(mode);
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setMode(mode);

        return new CommandResult(String.format(MESSAGE_SUCCESS, mode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModeCommand // instanceof handles nulls
                && mode.equals(((ModeCommand) other).mode));
    }
}
