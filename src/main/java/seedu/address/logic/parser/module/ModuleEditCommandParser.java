package seedu.address.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ModuleEditCommand object.
 */
public class ModuleEditCommandParser implements Parser<ModuleEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ModuleEditCommand
     * and returns an ModuleEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ModuleEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION);

        int mode = 0;
        Index index = null;
        ParseException invalidFormatException = null;
        String preamble = argMultimap.getPreamble();

        if (argMultimap.getValue(PREFIX_MODULE_CODE) == null && argMultimap.getValue(PREFIX_DESCRIPTION) == null
                || preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ModuleEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.numOfValuesPresent(PREFIX_MODULE_CODE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_DESCRIPTION) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
        }

        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_INVALID_INDEX)) {
                throw new ParseException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            }
            invalidFormatException = pe;
            mode = 1;
        }

        ModuleEditCommand.EditModuleDescriptor editModuleDescriptor = new ModuleEditCommand.EditModuleDescriptor();
        if (mode == 1 && !ModuleCode.isValidModuleCode(preamble)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ModuleEditCommand.MESSAGE_USAGE), invalidFormatException);
        }

        if (argMultimap.getValue(PREFIX_MODULE_CODE) != null) {
            editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE)));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION) != null) {
            editModuleDescriptor.setDescription(ParserUtil.parseDescription(
                    parseFieldForEdit(argMultimap.getValue(PREFIX_DESCRIPTION))));
        }

        if (mode == 0) {
            return new ModuleEditCommand(index, editModuleDescriptor);
        } else { // User uses no index
            return new ModuleEditCommand(new ModuleCode(preamble), editModuleDescriptor);
        }
    }

    /**
     * Parses the given {@code String} of arguments and returns the {@code String} for the field for execution.
     * If the given {@code String} of arguments is an empty string, it will be parsed into a null Object.
     *
     * @param args the arguments for the field.
     * @return the {@code String} for the field.
     */
    private String parseFieldForEdit(String args) {
        if (args.equals("")) {
            return null;
        } else {
            return args;
        }
    }
}
