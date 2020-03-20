package tatracker.logic.parser;

import java.util.stream.Stream;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.AddModuleCommand;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.module.Module;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_MODULE);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddModuleCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseValue(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        String moduleCode = ParserUtil.parseValue(argMultimap.getValue(CliSyntax.PREFIX_MODULE).get());

        Module module = new Module(moduleCode, name);

        return new AddModuleCommand(module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
