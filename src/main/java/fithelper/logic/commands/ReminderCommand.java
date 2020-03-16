package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Displays the reminders stored by FitHelper.
 */
public class ReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the pending plans. ";

    public static final String MESSAGE_SUCCESS = "Now you can view all undone tasks ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS), HOME, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand);
    }
}
