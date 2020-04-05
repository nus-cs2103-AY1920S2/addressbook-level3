package seedu.address.logic.commands.admin;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an admin command.
 */
public abstract class AdminCommand extends Command {

    public static final String COMMAND_WORD = "admin";
    public static final String ADMIN_FETCH = "fetch";
    public static final String ADMIN_SAVE = "save";
    public static final String ADMIN_DELETE = "delete";
    public static final String ADMIN_DATES = "dates";

    public static final String MESSAGE_USAGE = "These are the various admin commands:\n"
            + COMMAND_WORD + ": displays the last updated admin list of the class\n"
            + COMMAND_WORD + " " + ADMIN_DATES + ": displays the list of dates that has admin information of the "
            + "class\n"
            + COMMAND_WORD + " " + ADMIN_SAVE + ": saves the last updated admin list of class as today's date\n"
            + COMMAND_WORD + " " + ADMIN_FETCH + " YYYY-MM-DD: fetches the admin details of class at specified date\n"
            + COMMAND_WORD + " " + ADMIN_DELETE + " YYYY-MM-DD: deletes the admin details of class at specified "
            + "date\n";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}
