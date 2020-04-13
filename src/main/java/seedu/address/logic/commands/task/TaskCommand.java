package seedu.address.logic.commands.task;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Represents a Task command with hidden internal logic and the ability to be executed.
 */
public abstract class TaskCommand extends Command {

    public static final String ADD_FORMAT = String.format("%s %s %s MOD_CODE %s DESCRIPTION %s DATE %s TIME",
            COMMAND_GROUP_TASK, COMMAND_WORD_ADD,
            PREFIX_MODULE_CODE, PREFIX_DESCRIPTION, PREFIX_ON, PREFIX_AT);
    public static final String EDIT_FORMAT = String.format(
            "%s %s MOD_CODE ID_NUMBER [%s DESCRIPTION] [%s DATE/non] [%s TIME]",
            COMMAND_GROUP_TASK, COMMAND_WORD_EDIT, PREFIX_DESCRIPTION, PREFIX_ON, PREFIX_AT);
    public static final String DELETE_FORMAT = String.format("%s %s MOD_CODE ID_NUMBER",
            COMMAND_GROUP_TASK, COMMAND_WORD_DELETE);
    public static final String SEARCH_FORMAT = String.format("%s %s %s DAY %s MONTH %s YEAR",
            COMMAND_GROUP_TASK, COMMAND_WORD_SEARCH,
            PREFIX_DATE, PREFIX_MONTH, PREFIX_YEAR);
    public static final String LIST_FORMAT = String.format("%s %s", COMMAND_GROUP_TASK, COMMAND_WORD_LIST);
    public static final String MARK_AS_DONE_FORMAT = String.format("%s %s %s MOD_CODE %s ID_NUMBER",
            COMMAND_GROUP_TASK, COMMAND_WORD_DONE, PREFIX_MODULE_CODE, PREFIX_TASK_ID);
    public static final String LIST_DONE_FORMAT = String.format("%s %s", COMMAND_GROUP_TASK, COMMAND_WORD_UNDONE);
    public static final String TASK_FOR_MODULE_FORMAT = String.format("%s %s %s MOD_CODE",
            COMMAND_GROUP_TASK, COMMAND_WORD_MODULE, PREFIX_MODULE_CODE);

    public static final List<String> ALL_COMMAND_FORMATS = List.of(
            ADD_FORMAT, MARK_AS_DONE_FORMAT, LIST_FORMAT, EDIT_FORMAT, DELETE_FORMAT,
            SEARCH_FORMAT, LIST_DONE_FORMAT, TASK_FOR_MODULE_FORMAT);

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
