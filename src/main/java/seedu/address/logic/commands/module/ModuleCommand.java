package seedu.address.logic.commands.module;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a module command with hidden internal logic and the ability to be executed.
 */
public abstract class ModuleCommand extends Command {

    public static final String ADD_FORMAT = String.format(
            "%s %s %s MOD_CODE [%s DESCRIPTION]",
            COMMAND_GROUP_MOD, COMMAND_WORD_ADD, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION);
    public static final String DELETE_FORMAT_INDEX = String.format("%s %s INDEX",
            COMMAND_GROUP_MOD, COMMAND_WORD_DELETE);
    public static final String DELETE_FORMAT_MOD_CODE = String.format("%s %s MOD_CODE",
            COMMAND_GROUP_MOD, COMMAND_WORD_DELETE);
    public static final String EDIT_FORMAT_INDEX = String.format(
            "%s %s INDEX [%s NEW_MOD_CODE] [%s DESCRIPTION]",
            COMMAND_GROUP_MOD, COMMAND_WORD_EDIT, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION);
    public static final String EDIT_FORMAT_MOD_CODE = String.format(
            "%s %s MOD_CODE[%s NEW_MOD_CODE] [%s DESCRIPTION]",
            COMMAND_GROUP_MOD, COMMAND_WORD_EDIT, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION);
    public static final String LIST_FORMAT = String.format("%s %s", COMMAND_GROUP_MOD, COMMAND_WORD_LIST);
    public static final String VIEW_FORMAT_INDEX = String.format("%s %s INDEX",
            COMMAND_GROUP_MOD, COMMAND_WORD_VIEW);
    public static final String VIEW_FORMAT_MOD_CODE = String.format("%s %s MOD_CODE",
            COMMAND_GROUP_MOD, COMMAND_WORD_VIEW);

    public static final List<String> ALL_COMMAND_WORDS = List.of(
            COMMAND_WORD_ADD, COMMAND_WORD_DELETE, COMMAND_WORD_EDIT, COMMAND_WORD_LIST, COMMAND_WORD_VIEW);
    public static final List<String> ALL_COMMAND_FORMATS = List.of(
            ADD_FORMAT, DELETE_FORMAT_INDEX, DELETE_FORMAT_MOD_CODE,
            EDIT_FORMAT_INDEX, EDIT_FORMAT_MOD_CODE, LIST_FORMAT, VIEW_FORMAT_INDEX, VIEW_FORMAT_MOD_CODE);

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
