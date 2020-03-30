package tatracker.logic.parser.module;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import tatracker.logic.commands.module.EditModuleCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.module.Module;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE));
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULE).get();

        Module module = new Module(moduleCode);

        String newName = ParserUtil.parseValue(argMultimap.getValue(PREFIX_NAME).get());

        return new EditModuleCommand(module, newName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
