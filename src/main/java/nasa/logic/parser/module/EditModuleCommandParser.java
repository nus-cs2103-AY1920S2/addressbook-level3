package nasa.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import java.util.List;
import java.util.NoSuchElementException;

import nasa.logic.commands.module.EditModuleCommand;
import nasa.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.Parser;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/* @@author don-tay */
/**
 * Parses input arguments and creates a new EditModuleCommand object.
 * Valid format: edit m/MODULE CODE [m/MODULE CODE] [n/MODULE NAME]
 * Exactly 1 or 2 module code must be provided.
 * Any number of module name can be provided, only the last module name will be used.
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    private static final int NUM_ARGS_TO_EDIT_MODULE_CODE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String argsWithWhitespace = addStartWhitespace(args); // helper method to provide empty string as preamble arg
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(argsWithWhitespace, PREFIX_MODULE, PREFIX_MODULE_NAME);

        ModuleCode moduleCode;

        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getFirstValue(PREFIX_MODULE).get());
        } catch (NoSuchElementException ne) { // case when no module code is provided
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT,
                    ne);
        }

        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();

        List<String> allModuleCodeParsed = argMultimap.getAllValues(PREFIX_MODULE);

        // case when exactly 2 module code is entered, allowing for module code to be edited
        if (isModuleCodeEditable(allModuleCodeParsed)) {
            editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get()));
        }

        if (argMultimap.getValue(PREFIX_MODULE_NAME).isPresent()) {
            editModuleDescriptor.setModuleName(ParserUtil.parseModuleName(
                    argMultimap.getValue(PREFIX_MODULE_NAME).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            if (isExcessModuleCodeParsed(allModuleCodeParsed)) { // case when more than 2 module code provided
                throw new ParseException((EditModuleCommand.EXCESS_MODULE_CODE));
            }
            throw new ParseException(EditModuleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditModuleCommand(moduleCode, editModuleDescriptor);
    }

    /**
     * Helper method to add a whitespace to the start of args, as ArgumentTokenizer requires a preamble, which
     * the user input format for edit module does not have.
     * @param args Original argument passed to EditModuleCommandParser class to be parsed
     * @return original argument with an additional whitespace at the start
     */
    private String addStartWhitespace(String args) {
        return " " + args;
    }

    /**
     * Checks if two module code prefixes are parsed, which indicates module code will be edited.
     * If non-two '/m', received, returns false
     * @param moduleCodes list containing all {@code moduleCode} parsed
     * @return true if exactly two module codes provided, otherwise false
     */
    private boolean isModuleCodeEditable(List<String> moduleCodes) {
        if (moduleCodes.size() == NUM_ARGS_TO_EDIT_MODULE_CODE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if more than two module code prefixes are parsed, which indicates module code will not be edited.
     * If more than two '/m', received, returns true
     * @param moduleCodes list containing all {@code moduleCode} parsed
     * @return true if more than two module codes provided, otherwise false
     */
    private boolean isExcessModuleCodeParsed(List<String> moduleCodes) {
        if (moduleCodes.size() > NUM_ARGS_TO_EDIT_MODULE_CODE) {
            return true;
        }
        return false;
    }
}
