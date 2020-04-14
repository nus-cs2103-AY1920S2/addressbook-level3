package fithelper.logic.weight;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;
import fithelper.model.WeightRecords;
import fithelper.model.profile.Profile;

/**
 * Clears the address book.
 */
public class ClearWeightCommand extends Command {

    public static final String COMMAND_WORD = "clearWeight";
    public static final String MESSAGE_SUCCESS = "Weight Records have been cleared!";

    private static final String MESSAGE_COMMIT = "Clear all weight records.";

    private static final Logger logger = LogsCenter.getLogger(ClearWeightCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setWeightRecords(new WeightRecords());

        // update profile.
        Profile profile = model.getUserProfile().getUserProfile();
        profile.setCurrentWeight(null);
        profile.setCurrentBmi(null);

        model.commit(MESSAGE_COMMIT);
        logger.info("Clear the current FitHelper");

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.DisplayedPage.WEIGHT);
    }
}
