package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.ProfileList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Profile list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setProfileList(new ProfileList());
        model.clearDeadlineList();
        return new CommandResult(MESSAGE_SUCCESS, false);
    }
}
