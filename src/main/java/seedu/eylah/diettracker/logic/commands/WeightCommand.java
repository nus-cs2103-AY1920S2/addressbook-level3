package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Adds the weight of the user in kilograms.
 */
public class WeightCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "weight";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": \n"
            + "Parameters: WEIGHT (must be in kilograms)\n"
            + "Example: " + COMMAND_WORD + " 65.3";

    public static final String MESSAGE_ADD_WEIGHT_SUCCESS = "Added Weight: %1$s";

    private final Weight weight;

    /**
     * Constructor for Command that includes input Weight.
     */
    public WeightCommand(Weight weight) {
        requireNonNull(weight);
        this.weight = weight;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);

        model.setWeight(weight);

        return new CommandResult(String.format(MESSAGE_ADD_WEIGHT_SUCCESS, weight.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightCommand // instanceof handles nulls
                && weight.equals(((WeightCommand) other).weight)); // state check
    }
}
