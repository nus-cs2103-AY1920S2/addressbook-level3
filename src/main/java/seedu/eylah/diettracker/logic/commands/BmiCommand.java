package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.self.Bmi;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Calculates BMI. BMI is calculated based on one of the following:
 * If there is height and weight input, then it will be based on that.
 * If not, it will calculate based on the internal height and weight
 * previously recorded.
 */
public class BmiCommand extends Command {

    public static final String COMMAND_WORD = "bmi";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": \n"
            + "Parameters: [-h HEIGHT (must be in centimeters)] [-w WEIGHT (must be in kilograms)]\n"
            + "Example: " + COMMAND_WORD + "-h 170 -w 65";

    public static final String MESSAGE_CALCULATE_BMI_SUCCESS = "The BMI Calculated is: %1$s\n"
            + "Your BMI is in the %1$s category.";

    private final Height height;
    private final Weight weight;
    private final Bmi bmi;

    /**
     * Constructor for Command that does not include Height and Weight.
     */
    public BmiCommand() {
        height = Self.getHeight();
        weight = Self.getWeight();
        bmi = new Bmi(height, weight);
    }

    /**
     * Constructor for Command that includes input Height and Weight.
     */
    public BmiCommand(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
        this.bmi = new Bmi(height, weight);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (bmi.getBmi() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new CommandResult(String.format(MESSAGE_CALCULATE_BMI_SUCCESS, bmi));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BmiCommand // instanceof handles nulls
                && height.equals(((BmiCommand) other).height) // state check
                && weight.equals(((BmiCommand) other).weight)); // state check
    }
}
