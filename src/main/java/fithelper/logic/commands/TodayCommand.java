package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.TODAY;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class TodayCommand extends Command {

    public static final String COMMAND_WORD = "today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays today page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Today Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS), TODAY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodayCommand);
    }
}
