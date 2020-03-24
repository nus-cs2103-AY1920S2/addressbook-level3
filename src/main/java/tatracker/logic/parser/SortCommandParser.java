package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import tatracker.logic.commands.SortCommand;
import tatracker.logic.commands.SortGroupCommand;
import tatracker.logic.commands.SortModuleCommand;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE,
                PREFIX_MODULE, PREFIX_GROUP);

        if (args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String type = ParserUtil.parseValue(argMultimap.getValue(PREFIX_TYPE).get());

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            String moduleCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get());

            if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
                String groupCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_GROUP).get());
                return new SortGroupCommand(groupCode, moduleCode, type);
            } else {
                return new SortModuleCommand(moduleCode, type);
            }
        } else {
            return new SortCommand(type);
        }
    }
}
