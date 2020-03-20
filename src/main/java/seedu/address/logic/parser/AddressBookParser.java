package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTransportBookingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearTransportBookingCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.activity.AddActivityCommand;
import seedu.address.logic.commands.fixedexpense.AddFixedExpenseCommand;
import seedu.address.logic.commands.fixedexpense.ClearFixedExpenseCommand;
import seedu.address.logic.commands.fixedexpense.DeleteFixedExpenseCommand;
import seedu.address.logic.commands.packinglist.AddItemCommand;
import seedu.address.logic.parser.activity.AddActivityParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.fixedexpense.AddFixedExpenseParser;
import seedu.address.logic.parser.fixedexpense.DeleteFixedExpenseCommandParser;
import seedu.address.logic.parser.packinglist.AddItemParser;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddFixedExpenseCommand.COMMAND_WORD:
            return new AddFixedExpenseParser().parse(arguments);

        case ClearFixedExpenseCommand.COMMAND_WORD:
            return new ClearFixedExpenseCommand();

        case DeleteFixedExpenseCommand.COMMAND_WORD:
            return new DeleteFixedExpenseCommandParser().parse(arguments);

        case AddTransportBookingCommand.COMMAND_WORD:
            return new AddTransportBookingCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
            return new AddItemParser().parse(arguments);

        case ClearTransportBookingCommand.COMMAND_WORD:
            return new ClearTransportBookingCommand();

        case AddActivityCommand.COMMAND_WORD:
            return new AddActivityParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
