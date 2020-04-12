package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.Mode;

/**
 * Sets the Mode of the Diet Tracker depending on user preferences. The 3 modes available are
 * -l for weight loss, -g for weight gain, and -m for maintaining weight.
 */
public class ModeCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the mode in which you want to use the diet app "
            + "for tracking. "
            + "Parameters: [-l] [-g] [-m]";

    public static final String MESSAGE_SUCCESS = "Mode Change Successful: %1$s";
    public static final String MESSAGE_FLAG_NOT_PROVIDED = "Please provide a flag [-g]/[-m]/[-l]!";

    private final Mode mode;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public ModeCommand(Mode mode) {
        requireNonNull(mode);
        this.mode = mode;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
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
