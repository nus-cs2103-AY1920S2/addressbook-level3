package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.self.Bmi;
import seedu.eylah.diettracker.model.self.Height;
//import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Calculates BMI. BMI is calculated based on one of the following:
 * If there is height and weight input, then it will be based on that.
 * If not, it will calculate based on the internal height and weight
 * previously recorded.
 */
public class BmiCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "bmi";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": \n"
            + "Parameters: [-h HEIGHT (must be in centimeters)] [-w WEIGHT (must be in kilograms)]\n"
            + "Example: " + COMMAND_WORD + " -h 170 -w 65";

    public static final String MESSAGE_CALCULATE_BMI_SUCCESS = "The BMI Calculated is: %1$s\n"
            + "Your BMI is in the %2$s category.";

    private Bmi bmi;
    private Height height;
    private Weight weight;

    /**
     * Constructor for Command that does not include Height and Weight.
     */
    public BmiCommand() {
        height = new Height(0);
        weight = new Weight(0);
    }

    /**
     * Constructor for Command that includes input Height and Weight.
     */
    public BmiCommand(Height height, Weight weight) {
        requireNonNull(height);
        requireNonNull(weight);
        this.height = height;
        this.weight = weight;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);

        Height storedHeight = model.getHeight();
        Weight storedWeight = model.getWeight();

        if (height.isZero() && weight.isZero()) {
            if (storedHeight.isZero() && storedWeight.isZero()) {
                throw new CommandException("Please provide height and weight after the -h and -w flags respectively "
                        + "as you have not stored them yet!");
            }
        }

        if (height.isZero()) {
            if (storedHeight.isNotZero()) {
                height = storedHeight;
            } else {
                throw new CommandException("Please provide height after the -h flag as you have not stored your "
                        + "height yet");
            }
        }

        if (weight.isZero()) {
            if (storedWeight.isNotZero()) {
                weight = storedWeight;
            } else {
                throw new CommandException("Please provide weight after the -w flag as you have not stored your "
                        + "weight yet!");
            }
        }

        bmi = new Bmi(height, weight);


        if (bmi.getBmi() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new CommandResult(String.format(MESSAGE_CALCULATE_BMI_SUCCESS, bmi, bmi.getCategory()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BmiCommand // instanceof handles nulls
                && height.equals(((BmiCommand) other).height) // state check
                && weight.equals(((BmiCommand) other).weight)); // state check
    }
}
