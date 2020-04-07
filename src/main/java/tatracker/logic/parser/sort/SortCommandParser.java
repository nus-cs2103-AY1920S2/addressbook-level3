package tatracker.logic.parser.sort;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.sort.SortCommand;
import tatracker.logic.commands.sort.SortGroupCommand;
import tatracker.logic.commands.sort.SortModuleCommand;
import tatracker.logic.commands.sort.SortType;
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.DETAILS.getUsage()));
        }

        final String commandWord = matcher.group("commandWord");
        final String args = matcher.group("arguments");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, TYPE, MODULE, GROUP);

        if (!argMultimap.arePrefixesPresent(TYPE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortCommand.DETAILS.getUsage()));
        }

        SortType type = ParserUtil.parseSortType(argMultimap.getValue(TYPE).get());

        boolean hasModule = argMultimap.getValue(MODULE).isPresent();
        boolean hasGroup = argMultimap.getValue(GROUP).isPresent();

        String moduleCode = argMultimap.getValue(MODULE).map(String::trim).orElse("").toUpperCase();
        String groupCode = argMultimap.getValue(GROUP).map(String::trim).orElse("").toUpperCase();

        switch (commandWord) {

        case CommandWords.SORT_ALL:
            return new SortCommand(type);

        case CommandWords.SORT_MODULE:
            if (hasModule) {
                return new SortModuleCommand(type, moduleCode);
            }
            break;

        case CommandWords.SORT_GROUP:
            if (hasModule && hasGroup) {
                return new SortGroupCommand(type, groupCode, moduleCode);
            }
            break;

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortCommand.DETAILS.getUsage()));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.DETAILS.getUsage()));
    }
}
