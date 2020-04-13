package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all tutorials in TAble.
 */
public class ListTutorialCommand extends Command {
    public static final String COMMAND_WORD = "listTutorial";

    public static final String MESSAGE_SUCCESS = COMMAND_WORD + ": Listed all tutorials\n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
