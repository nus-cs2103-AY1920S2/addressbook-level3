package seedu.address.logic.commands.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DATES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays the available list of dates that has admin information of the class.
 */
public class AdminDatesCommand extends AdminCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ADMIN_DATES
            + ": to display the list of dates that has admin information of the class";
    public static final String MESSAGE_SUCCESS = "List of dates with admin details of the class displayed!";

    /**
     * Creates an AdminDisplayCommand.
     */
    public AdminDatesCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredDateList(PREDICATE_SHOW_ALL_DATES);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) { //dummy method here
        return false;
    }
}
