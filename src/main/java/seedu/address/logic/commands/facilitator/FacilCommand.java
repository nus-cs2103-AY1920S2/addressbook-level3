package seedu.address.logic.commands.facilitator;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Represents a facilitator command with hidden internal logic and the ability to be executed.
 */
public abstract class FacilCommand extends Command {

    public static final String ADD_FORMAT = String.format(
            "%s %s %s FACILITATOR_NAME [%s PHONE] [%s EMAIL] [%s OFFICE] %s MOD_CODE [MORE_MOD_CODES]...",
            COMMAND_GROUP_FACIL, COMMAND_WORD_ADD,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_OFFICE, PREFIX_MODULE_CODE);
    public static final String ADD_FORMAT_PREFIX = String.format(
            "%s %s %s FACILITATOR_NAME [%s PHONE] [%s EMAIL] [%s OFFICE] %s MOD_CODE [%s MORE_MOD_CODES]...",
            COMMAND_GROUP_FACIL, COMMAND_WORD_ADD,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_OFFICE, PREFIX_MODULE_CODE, PREFIX_MODULE_CODE);
    public static final String DELETE_FORMAT_INDEX = String.format(
            "%s %s INDEX", COMMAND_GROUP_FACIL, COMMAND_WORD_DELETE);
    public static final String DELETE_FORMAT_NAME = String.format(
            "%s %s FACILITATOR_NAME", COMMAND_GROUP_FACIL, COMMAND_WORD_DELETE);
    public static final String EDIT_FORMAT_INDEX = String.format(
            "%s %s INDEX [%s FACILITATOR_NAME] [%s PHONE] [%s EMAIL] [%s OFFICE] [%s MOD_CODES...]",
            COMMAND_GROUP_FACIL, COMMAND_WORD_EDIT,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_OFFICE, PREFIX_MODULE_CODE);
    public static final String EDIT_FORMAT_NAME = String.format(
            "%s %s FACILITATOR_NAME [%s FACILITATOR_NAME] [%s PHONE] [%s EMAIL] [%s OFFICE] [%s MOD_CODES...]",
            COMMAND_GROUP_FACIL, COMMAND_WORD_EDIT,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_OFFICE, PREFIX_MODULE_CODE);
    public static final String FIND_FORMAT =
            String.format("%s %s FACILITATOR_NAME [MORE_FACILITATOR_NAMES]...", COMMAND_GROUP_FACIL, COMMAND_WORD_FIND);
    public static final String LIST_FORMAT = String.format("%s %s", COMMAND_GROUP_FACIL, COMMAND_WORD_LIST);

    public static final List<String> ALL_COMMAND_WORDS = List.of(
            COMMAND_WORD_ADD, COMMAND_WORD_DELETE, COMMAND_WORD_EDIT, COMMAND_WORD_FIND, COMMAND_WORD_LIST);
    public static final List<String> ALL_COMMAND_FORMATS = List.of(
            ADD_FORMAT, ADD_FORMAT_PREFIX, DELETE_FORMAT_INDEX, DELETE_FORMAT_NAME, EDIT_FORMAT_INDEX, EDIT_FORMAT_NAME,
            FIND_FORMAT, LIST_FORMAT);

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
