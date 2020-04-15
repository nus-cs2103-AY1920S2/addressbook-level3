package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all contacts with birthdays that are upcoming in the next five days (including today).
 */
public class ShowBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "(ab)birthday";
    public static final String COMMAND_FUNCTION = "Lists all contacts with birthdays that are upcoming in the next five"
        + " days (including today)";
    public static final String MESSAGE_SUCCESS = "Birthdays for the next five days (today inclusive) is listed on the "
        + "rightmost panel.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n";

    public ShowBirthdayCommand() {}

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ShowBirthdayCommand);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
