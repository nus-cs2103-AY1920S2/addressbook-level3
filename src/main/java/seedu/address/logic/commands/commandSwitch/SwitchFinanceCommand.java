package seedu.address.logic.commands.commandSwitch;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandList.ListCommand;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class SwitchFinanceCommand extends ListCommand {

    public static final String COMMAND_WORD = "f";

    public static final String COMMAND_WORD_ALT = "finance";

    public static final String MESSAGE_SUCCESS = "Switched to Finance Tab";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.getMainWindow().callSwitchToFinance();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
