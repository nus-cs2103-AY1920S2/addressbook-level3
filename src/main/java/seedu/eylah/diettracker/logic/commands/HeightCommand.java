package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;

/**
 * Adds the height of the user in centimeters.
 */
public class HeightCommand extends Command {

    public static final String COMMAND_WORD = "height";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": \n"
            + "Parameters: HEIGHT (must be in centimeters)\n"
            + "Example: " + COMMAND_WORD + "170";

    public static final String MESSAGE_ADD_HEIGHT_SUCCESS = "Added Height: %1$s";

    private final Height height;

    /**
     * Constructor for Command that includes input Height.
     */
    public HeightCommand(Height height) {
        this.height = height;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (height.getHeightFloat() <= 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        Self.setHeight(height);

        return new CommandResult(String.format(MESSAGE_ADD_HEIGHT_SUCCESS, height.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HeightCommand // instanceof handles nulls
                && height.equals(((HeightCommand) other).height)); // state check
    }
}
