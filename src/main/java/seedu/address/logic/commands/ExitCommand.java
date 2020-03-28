package seedu.address.logic.commands;

import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;

import static java.util.Objects.requireNonNull;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
