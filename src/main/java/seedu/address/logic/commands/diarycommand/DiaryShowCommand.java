package seedu.address.logic.commands.diarycommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Dummy java docs.
 */
public abstract class DiaryShowCommand extends Command {
    public static final String COMMAND_WORD = "diaryShow";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a particular diary entry\n"
            + COMMAND_WORD + ": Shows a diary entry by specifying the entry ID "
            + "Parameters: "
            + PREFIX_ENTRY_ID + "ENTRY ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_ID + "1 "
            + "or\n"
            + COMMAND_WORD + ": Shows a diary entry by specifying the date "
            + "Parameters: "
            + PREFIX_DIARY_DATE + " DATE"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DIARY_DATE + " 01-04-2020 ";

    public abstract CommandResult execute(Model model) throws CommandException;
}
