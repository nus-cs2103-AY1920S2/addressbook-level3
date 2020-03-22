package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditModuleCommand;
import nasa.logic.commands.EditModuleCommand.EditModuleDescriptor;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    private static final int EDIT_MODULE_CODE = 2; // Number of modules to trigger an edit
    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_MODULE_NAME);

        ModuleCode moduleCode;

        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getFirstValue(PREFIX_MODULE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE),
                    pe);
        } catch (NoSuchElementException ne) { // case when no module code is provided
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE),
                    ne);
        }

        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        if (isModuleCodeEdited(argMultimap.getAllValues(PREFIX_MODULE))) {
            editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULE_NAME).isPresent()) {
            editModuleDescriptor.setModuleName(ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MODULE_NAME).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditModuleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditModuleCommand(moduleCode, editModuleDescriptor);
    }

    /**
     * Checks if two module code prefixes are parsed, which indicates module code will be edited.
     * If non-two '/m', received, returns false
     * @param moduleCodes list containing original {@code moduleCode}(compulsory) and new {@code moduleCode}(optional)
     * @return true if exactly 2 module codes provided, otherwise false
     */
    private boolean isModuleCodeEdited(List<String> moduleCodes) {
        if (moduleCodes.size() == EDIT_MODULE_CODE) {
            return true;
        }
        return false;
    }
}
