package seedu.address.logic.commands.commandSwitch;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandList.ListCommand;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class SwitchCourseCommand extends ListCommand {

    public static final String COMMAND_WORD = "c";

    public static final String COMMAND_WORD_ALT = "course";

    public static final String MESSAGE_SUCCESS = "Switched to Course Tab";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.getMainWindow().callSwitchToCourse();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
