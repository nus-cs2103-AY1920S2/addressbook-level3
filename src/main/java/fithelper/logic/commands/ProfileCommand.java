package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.PROFILE;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class ProfileCommand extends Command {

    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the user profile page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at User Profile Page ~";

    private static final String MESSAGE_COMMIT = "Switch to profile page";

    private static final Logger logger = LogsCenter.getLogger(ProfileCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.commit(MESSAGE_COMMIT);
        logger.info("Switched to Profile Page");

        return new CommandResult(String.format(MESSAGE_SUCCESS), PROFILE, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfileCommand);
    }
}
