package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;

/**
 * Display session statistics to user.
 */
public class DisplayCommand extends LogCommand {
    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_SUCCESS = "Displaying report!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
