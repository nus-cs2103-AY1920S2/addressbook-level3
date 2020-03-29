package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

import tatracker.logic.commands.session.FilterModuleTSSCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.DoneSessionPredicate;

/**
 * Parses input arguments and creates a new FilterModuleCommand object
 */
public class FilterModuleTSSCommandParser implements Parser<FilterModuleTSSCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterSessionCommand
     * and returns a FilterSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterModuleTSSCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterModuleTSSCommand.MESSAGE_USAGE));
        }

        String moduleCode = "";

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get());
        }
        return new FilterModuleTSSCommand(new DoneSessionPredicate(moduleCode));
    }
}
