package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Adds the weight of the user in kilograms.
 */
public class WeightCommand extends Command {

    public static final String COMMAND_WORD = "weight";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": \n"
            + "Parameters: WEIGHT (must be in kilograms)\n"
            + "Example: " + COMMAND_WORD + "65.3";

    public static final String MESSAGE_ADD_WEIGHT_SUCCESS = "Added Weight: %1$s";

    private final Weight weight;

    /**
     * Constructor for Command that includes input Weight.
     */
    public WeightCommand(Weight weight) {
        this.weight = weight;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (weight.getWeightFloat() <= 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        Self.setWeight(weight);

        return new CommandResult(String.format(MESSAGE_ADD_WEIGHT_SUCCESS, weight.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightCommand // instanceof handles nulls
                && weight.equals(((WeightCommand) other).weight)); // state check
    }
}
