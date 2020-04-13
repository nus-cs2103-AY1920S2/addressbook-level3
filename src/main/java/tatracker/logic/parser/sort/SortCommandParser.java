//@@author aakanksha-rai

package tatracker.logic.parser.sort;

import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.commons.core.Messages;
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
public class SortCommandParser implements Parser<SortGroupCommand> {
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
    public SortGroupCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(Messages.getInvalidCommandMessage(HelpCommand.DETAILS.getUsage()));
        }

        final String commandWord = matcher.group("commandWord");
        final String args = matcher.group("arguments");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, TYPE, MODULE, GROUP);

        if (!argMultimap.arePrefixesPresent(TYPE) || !argMultimap.getPreamble().isEmpty()) {
            switch(commandWord) {
            case CommandWords.SORT_ALL:
                throw new ParseException(Messages.getInvalidCommandMessage(SortCommand.DETAILS.getUsage()));
            case CommandWords.SORT_GROUP:
                throw new ParseException(Messages.getInvalidCommandMessage(SortGroupCommand.DETAILS.getUsage()));
            case CommandWords.SORT_MODULE:
                throw new ParseException(Messages.getInvalidCommandMessage(SortModuleCommand.DETAILS.getUsage()));
            default:
                throw new ParseException(Messages.getUnknownCommandWithHelpMessage());
            }
        }

        boolean hasType = argMultimap.getValue(TYPE).isPresent();

        SortType type = ParserUtil.parseSortType(argMultimap.getValue(TYPE).get());

        boolean hasModule = argMultimap.getValue(MODULE).isPresent();
        boolean hasGroup = argMultimap.getValue(GROUP).isPresent();

        String moduleCode = argMultimap.getValue(MODULE).map(String::trim).orElse("").toUpperCase();
        String groupCode = argMultimap.getValue(GROUP).map(String::trim).orElse("").toUpperCase();

        switch (commandWord) {

        case CommandWords.SORT_ALL:
            if (!hasType) {
                throw new ParseException(Messages.getInvalidCommandMessage(SortCommand.DETAILS.getUsage()));
            }
            return new SortCommand(type);

        case CommandWords.SORT_MODULE:
            if (!hasModule || !hasType) {
                throw new ParseException(Messages.getInvalidCommandMessage(SortModuleCommand.DETAILS.getUsage()));
            }
            return new SortModuleCommand(type, moduleCode);

        case CommandWords.SORT_GROUP:
            if (!hasModule || !hasGroup || !hasType) {
                throw new ParseException(Messages.getInvalidCommandMessage(SortGroupCommand.DETAILS.getUsage()));
            }
            return new SortGroupCommand(type, groupCode, moduleCode);

        default:
            throw new ParseException(Messages.getUnknownCommandWithHelpMessage());
        }
    }
}
