package fithelper.logic.weight;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.profile.Profile;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;

/**
 * Deletes a weight identified using it's displayed index from the address book.
 */
public class DeleteWeightCommand extends Command {

    public static final String COMMAND_WORD = "deleteWeight";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the weight identified by the date in the displayed line chart.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE(if don't have date, by default is today)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-01";

    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS = "Deleted weight on: %1$s";

    private static final String MESSAGE_COMMIT = "Delete a weight record";

    private static final Logger logger = LogsCenter.getLogger(DeleteWeightCommand.class);

    private final Date deleteDate;

    public DeleteWeightCommand(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Profile profile = model.getUserProfile().getUserProfile();

        Weight toDelete = model.getWeightByDate(deleteDate);
        if (toDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEIGHT_DATE);
        }
        logger.info(String.format("find to delete weight ", toDelete.toString()));

        assert toDelete != null;
        boolean isLast = deleteDate.value.isEqual(model.getLastWeightDate());

        model.deleteWeight(toDelete);
        if (isLast) {
            // update profile if latest weight record is deleted.
            Date newLastDate = new Date(model.getLastWeightDate().toString());
            Weight newLastWeight = model.getWeightByDate(newLastDate);
            profile.setCurrentWeight(newLastWeight.getWeightValue());
            profile.setCurrentBmi(newLastWeight.getBmi());
            logger.info(String.format("update profile with new current weight"));
        }

        model.commit(MESSAGE_COMMIT);
        logger.info("Deleted a weight");

        return new CommandResult(String.format(MESSAGE_DELETE_WEIGHT_SUCCESS, toDelete),
                CommandResult.DisplayedPage.WEIGHT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWeightCommand // instanceof handles nulls
                && deleteDate.equals(((DeleteWeightCommand) other).deleteDate)); // state check
    }
}
