package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.PROFILE;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class ProfileCommand extends Command {

    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the user profile page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at User Profile Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS), PROFILE, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfileCommand);
    }
}
