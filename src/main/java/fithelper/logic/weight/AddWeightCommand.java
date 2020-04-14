package fithelper.logic.weight;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.profile.Height;
import fithelper.model.profile.Profile;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;

/**
 * Adds a weight to Weight Records.
 */
public class AddWeightCommand extends Command {

    public static final String COMMAND_WORD = "addWeight";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a weight to Weight Records. "
            + "Parameters: "
            + PREFIX_VALUE + "WEIGHT VALUE "
            + "[" + PREFIX_DATE + "DATE(if don't have date, by default is today)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VALUE + "50.0"
            + PREFIX_DATE + "2020-03-01";

    public static final String MESSAGE_DUPLICATE_WEIGHT = "The weight record with the same date "
            + "already exists in Weight Records";
    public static final String MRSSAGE_FUTURE_WEIGHT = "How can you predict future weight?";
    public static final String MESSAGE_SUCCESS = "New Weight added: %1$s";
    private static final String MESSAGE_COMMIT = "Add a weight";

    private static final Logger logger = LogsCenter.getLogger(AddWeightCommand.class);

    private final Date toAddDate;
    private final WeightValue toAddWeightValue;

    /**
     * Creates an AddCommand to add the specified {@code Weight}
     */
    public AddWeightCommand(Date date, WeightValue weightValue) {
        requireNonNull(date);
        requireNonNull(weightValue);
        toAddDate = date;
        toAddWeightValue = weightValue;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (toAddDate.value.isAfter(LocalDate.now())) {
            throw new CommandException(MRSSAGE_FUTURE_WEIGHT);
        }

        Profile profile = model.getUserProfile().getUserProfile();
        Height height = profile.getHeight();

        double bmiValue = toAddWeightValue.value / (height.value / 100.0) / (height.value / 100.0);
        Bmi toAddBmi = new Bmi(bmiValue);

        Weight toAdd = new Weight(toAddDate, toAddWeightValue, toAddBmi);

        if (model.hasWeight(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEIGHT);
        }

        // update profile.
        if (model.getLastWeightDate() == null || toAddDate.value.isAfter(model.getLastWeightDate())) {
            profile.setCurrentWeight(toAddWeightValue);
            profile.setCurrentBmi(toAddBmi);
        }
        logger.info(String.format("update profile with new current weight", toAdd.toString()));

        model.addWeight(toAdd);

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("Added a new weight [%s]", toAdd.toString()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                CommandResult.DisplayedPage.WEIGHT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWeightCommand // instanceof handles nulls
                && toAddDate.equals(((AddWeightCommand) other).toAddDate)
                && toAddWeightValue.equals(((AddWeightCommand) other).toAddWeightValue));
    }
}
