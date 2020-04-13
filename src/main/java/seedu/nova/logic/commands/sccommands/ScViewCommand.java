package seedu.nova.logic.commands.sccommands;

import static seedu.nova.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.nova.logic.commands.Command;

/**
 * The generic view command of schedule.
 * A figurehead for the SvViewCommandParser.
 */
public abstract class ScViewCommand extends Command {

    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views your schedule on a particular day. "
            + "Parameters: "
            + PREFIX_DATE + "[YYYY-MM-DD] \n"
            + COMMAND_WORD + " week: Views your schedule on a particular week. "
            + "Parameters: "
            + PREFIX_INDEX + "[week #]";

}
