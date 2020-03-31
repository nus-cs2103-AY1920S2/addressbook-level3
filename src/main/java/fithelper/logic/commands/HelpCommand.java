package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HELP;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.LogsCenter;
import fithelper.model.Model;

import java.util.logging.Logger;

/**
 * Adds a entry to FitHelper.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the help page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Help Page ~";

    private static final String MESSAGE_COMMIT = "Switch to help page";

    private static final Logger logger = LogsCenter.getLogger(HelpCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.commit(MESSAGE_COMMIT);
        logger.info("Switched to Help Page");

        return new CommandResult(String.format(MESSAGE_SUCCESS), HELP, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand);
    }
}
