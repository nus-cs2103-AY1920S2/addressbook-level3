package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ShowBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "(ab)birthday";

    public ShowBirthdayCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(String.format(Messages.MESSAGE_BIRTHDAYS_LISTED), false, false, false, false, true);
    }
}
