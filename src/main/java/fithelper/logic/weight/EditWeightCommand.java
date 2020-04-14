package fithelper.logic.weight;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
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
 * Edits a weight identified using it's displayed index from the address book.
 */
public class EditWeightCommand extends Command {

    public static final String COMMAND_WORD = "editWeight";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the weight identified by the date in the displayed line chart.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE(if don't have date, by default is today)] "
            + PREFIX_VALUE + "NEW VALUE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-01 "
            + PREFIX_VALUE + "55.8";

    public static final String MESSAGE_EDIT_WEIGHT_SUCCESS = "Edited weight on: %1$s";

    private static final String MESSAGE_COMMIT = "Edit a weight record";

    private static final Logger logger = LogsCenter.getLogger(EditWeightCommand.class);

    private final Date editDate;
    private final WeightValue editWeightValue;

    public EditWeightCommand(Date editDate, WeightValue editWeightValue) {
        this.editDate = editDate;
        this.editWeightValue = editWeightValue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Profile profile = model.getUserProfile().getUserProfile();
        Height height = profile.getHeight();

        Weight toEdit = model.getWeightByDate(editDate);
        if (toEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEIGHT_DATE);
        }
        logger.info(String.format("find to edit weight ", toEdit.toString()));

        assert toEdit != null;

        if (toEdit.getWeightValue().equals(editWeightValue)) {
            throw new CommandException("No weight value is changed");
        }

        boolean isLast = editDate.value.isEqual(model.getLastWeightDate());

        double bmiValue = editWeightValue.value / (height.value / 100.0) / (height.value / 100.0);
        Bmi editBmi = new Bmi(bmiValue);
        model.editWeight(toEdit, editWeightValue, editBmi);

        if (isLast) {
            // update profile if latest weight record is edited.
            Date newLastDate = new Date(model.getLastWeightDate().toString());
            Weight newLastWeight = model.getWeightByDate(newLastDate);
            profile.setCurrentWeight(newLastWeight.getWeightValue());
            profile.setCurrentBmi(newLastWeight.getBmi());
            logger.info(String.format("update profile with new current weight"));
        }

        model.commit(MESSAGE_COMMIT);
        logger.info("Edited a weight");

        return new CommandResult(String.format(MESSAGE_EDIT_WEIGHT_SUCCESS, toEdit),
                CommandResult.DisplayedPage.WEIGHT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditWeightCommand // instanceof handles nulls
                && editDate.equals(((EditWeightCommand) other).editDate)
                && editWeightValue.equals(((EditWeightCommand) other).editWeightValue)); // state check
    }
}
