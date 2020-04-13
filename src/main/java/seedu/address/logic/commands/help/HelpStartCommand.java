package seedu.address.logic.commands.help;

import static seedu.address.commons.core.Messages.HELP_START;

import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.commands.results.HelpCommandResult;
import seedu.address.model.Model;

/**
 * Format instructions for getting started.
 */
public class HelpStartCommand extends HelpCommand {

    /**
     * Opens a pop-up window with instructions for user on how to get started.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes changes made when command execute runs successfully.
     */
    @Override
    public CommandResult execute(Model model) {
        return new HelpCommandResult("", SHOWING_HELP_MESSAGE, model.getDisplayType(), HELP_START);
    }
}
