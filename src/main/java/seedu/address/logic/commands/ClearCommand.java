package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileList;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Profile list has been cleared!";


    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        profileManager.setProfileList(new ProfileList());
        profileManager.clearDeadlineList();
        profileManager.setDisplayedView((Profile) null);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
