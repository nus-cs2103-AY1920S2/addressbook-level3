package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;

//@@author jadetayy
/**
 * Lists all persons in the address book to the user.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE = "";

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) {
        requireNonNull(profileManager);

        if (profileManager.getDisplayedView().isEmpty()) {
            return new CommandResult(MESSAGE, false, false, true);
        }

        profileManager.setDisplayedView((Profile) null);
        return new CommandResult(MESSAGE, false, false, true);
    }
}
