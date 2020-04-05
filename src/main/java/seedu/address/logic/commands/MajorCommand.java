package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.nusmodule.Major;

/**
 * Update the major the user taken in NUS.
 */
public class MajorCommand extends Command {
    public static final String COMMAND_WORD = "major";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": State your major (e.g. Computer Science) "
            + "for the app to help you with module planning\n"
            + "Parameters: your major\n"
            + "Example: " + COMMAND_WORD + "Computer Science";

    public static final String MESSAGE_SUCCESS = "Major updated: ";

    private final Major majorTaken;

    /**
     * Creates an AddModuleCommand to add the specified {@code NusModule}
     */
    public MajorCommand(Major major) {
        requireNonNull(major);
        majorTaken = major;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateMajor(majorTaken);

        return new CommandResult(MESSAGE_SUCCESS + majorTaken);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorCommand // instanceof handles nulls
                && majorTaken.equals(((MajorCommand) other).majorTaken)); // state check
    }
}
