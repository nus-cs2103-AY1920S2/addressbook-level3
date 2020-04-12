package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.logic.commands.ClearCommand;
import seedu.delino.logic.commands.Command;
import seedu.delino.logic.commands.DeleteCommand;
import seedu.delino.logic.commands.DeliveredCommand;
import seedu.delino.logic.commands.EditCommand;
import seedu.delino.logic.commands.ExitCommand;
import seedu.delino.logic.commands.HelpCommand;
import seedu.delino.logic.commands.ImportCommand;
import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.logic.commands.ListCommand;
import seedu.delino.logic.commands.NearbyCommand;
import seedu.delino.logic.commands.ReturnCommand;
import seedu.delino.logic.commands.SearchCommand;

import seedu.delino.logic.commands.ShowCommand;
import seedu.delino.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class DelinoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(DelinoParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case InsertCommand.COMMAND_WORD:
            logger.fine("Insert Command word in user input.");
            return new InsertCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            logger.fine("Edit Command word in user input.");
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            logger.fine("Delete Command word in user input.");
            return new DeleteCommandParser().parse(arguments);

        case DeliveredCommand.COMMAND_WORD:
            logger.fine("Delivered Command word in user input.");
            return new DeliveredCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            logger.fine("Clear Command word in user input.");
            return new ClearCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            logger.fine("Import Command word in user input.");
            return new ImportCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            logger.fine("Search Command word in user input.");
            return new SearchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            logger.fine("List Command word in user input.");
            return new ListCommand(arguments);

        case NearbyCommand.COMMAND_WORD:
            logger.fine("Nearby Command word in user input.");
            return new NearbyCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            logger.fine("Exit Command word in user input.");
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            logger.fine("Help Command word in user input.");
            return new HelpCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            logger.fine("Show Command word in user input.");
            return new ShowCommand(arguments);

        case ReturnCommand.COMMAND_WORD:
            logger.fine("Return Command word in user input.");
            return new ReturnCommandParser().parse(arguments);

        default:
            logger.info("Error in user input.");
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
