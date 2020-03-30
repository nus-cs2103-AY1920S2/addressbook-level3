package tatracker.logic.parser.sort;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.HelpCommand;
import tatracker.logic.commands.sort.SortCommand;
import tatracker.logic.commands.sort.SortGroupCommand;
import tatracker.logic.commands.sort.SortModuleCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String args = matcher.group("arguments");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE,
                PREFIX_MODULE, PREFIX_GROUP);

        String type;
        String moduleCode;
        String groupCode;

        switch (commandWord) {

        case CommandWords.SORT_ALL:
            if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            type = ParserUtil.parseValue(argMultimap.getValue(PREFIX_TYPE).get());
            return new SortCommand(type);

        case CommandWords.SORT_MODULE:
            if (!argMultimap.getValue(PREFIX_TYPE).isPresent()
                    || !argMultimap.getValue(PREFIX_MODULE).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SortModuleCommand.MESSAGE_USAGE));
            }
            type = ParserUtil.parseValue(argMultimap.getValue(PREFIX_TYPE).get());
            moduleCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get());
            return new SortModuleCommand(moduleCode, type);

        case CommandWords.SORT_GROUP:
            if (!argMultimap.getValue(PREFIX_TYPE).isPresent() || !argMultimap.getValue(PREFIX_MODULE).isPresent()
                    || !argMultimap.getValue(PREFIX_GROUP).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGroupCommand.MESSAGE_USAGE));
            }
            type = ParserUtil.parseValue(argMultimap.getValue(PREFIX_TYPE).get());
            moduleCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_MODULE).get());
            groupCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_GROUP).get());
            return new SortGroupCommand(groupCode, moduleCode, type);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
