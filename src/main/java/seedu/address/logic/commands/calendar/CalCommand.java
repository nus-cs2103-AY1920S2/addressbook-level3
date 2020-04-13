package seedu.address.logic.commands.calendar;

import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Represents a calendar command with hidden internal logic and the ability to be executed.
 */
public abstract class CalCommand extends Command {

    public static final String CAL_VIEW_FORMAT = COMMAND_GROUP_CAL + " " + COMMAND_WORD_VIEW + " "
            + PREFIX_WEEK + " this/next";
    public static final String CAL_FIND_FORMAT = COMMAND_GROUP_CAL + " " + COMMAND_WORD_FIND
            + " empty";
    public static final List<String> ALL_COMMAND_FORMATS = List.of(CAL_VIEW_FORMAT, CAL_FIND_FORMAT);

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

}
