package tatracker.logic.parser.student;

import tatracker.logic.commands.student.FilterStudentViewCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;

/**
 * Parse input arguments and create a new FilterStudentViewCommand object
 */
public class FilterStudentViewCommandParser implements Parser<FilterStudentViewCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the FilterSessionCommand
     * and returns a FilterSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterStudentViewCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE,
                PREFIX_GROUP);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterStudentViewCommand.MESSAGE_INVALID_MODULE_CODE));
        }

        List<String> argsList = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            argsList.add((ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get())));
        }

        if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
            argsList.add((
                    ParserUtil.parseValue(argMultimap.getValue(PREFIX_GROUP).get())));
        }

        return new FilterStudentViewCommand(argsList);
    }

}
