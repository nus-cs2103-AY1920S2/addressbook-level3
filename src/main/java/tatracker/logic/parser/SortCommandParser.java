package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, TYPE,
                MODULE, GROUP);

        if (!argMultimap.getValue(TYPE).isPresent()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND);
        }

        String type = ParserUtil.parseValue(argMultimap.getValue(TYPE).get());

        if (argMultimap.getValue(MODULE).isPresent()) {
            String moduleCode = ParserUtil.parseValue(argMultimap.getValue(MODULE).get());

            if (argMultimap.getValue(GROUP).isPresent()) {
                String groupCode = ParserUtil.parseValue(argMultimap.getValue(GROUP).get());
                return new SortGroupCommand(groupCode, moduleCode, type);
            } else {
                return new SortModuleCommand(moduleCode, type);
            }
        } else {
            return new SortCommand(type);
        }
    }
}
