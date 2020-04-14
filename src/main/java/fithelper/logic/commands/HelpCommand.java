package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HELP;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the help page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Help Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS), HELP, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand);
    }
}
