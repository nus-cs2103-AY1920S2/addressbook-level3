package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_CATEGORY = "exit";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + ": exits CookingPapa.\n"
            + "Example: " + COMMAND_CATEGORY;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExitCommand); // instanceof handles nulls
    }

}
