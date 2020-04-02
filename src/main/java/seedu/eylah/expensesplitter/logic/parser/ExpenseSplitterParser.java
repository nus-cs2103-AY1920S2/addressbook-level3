package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.logic.commands.BackCommand;
import seedu.eylah.expensesplitter.logic.commands.ClearReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.Command;
import seedu.eylah.expensesplitter.logic.commands.DeleteItemCommand;
import seedu.eylah.expensesplitter.logic.commands.DoneReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.HelpCommand;
import seedu.eylah.expensesplitter.logic.commands.ListAmountCommand;
import seedu.eylah.expensesplitter.logic.commands.ListReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.NewReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;

/**
 * Parses user input for ExpenseSplitter.
 */
public class ExpenseSplitterParser {

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

        // New Command need to add to here!!!
        switch(commandWord) {
        case ListReceiptCommand.COMMAND_WORD:
            return new ListReceiptCommand(); // No Args so no need to Parse.

        case BackCommand.COMMAND_WORD:
            return new BackCommand(); // No Args so no need to Parse.

        case PaidCommand.COMMAND_WORD:
            return new PaidCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
            return new AddItemCommandParser().parse(arguments);

        case DeleteItemCommand.COMMAND_WORD:
            return new DeleteItemCommandParser().parse(arguments);

        case ListAmountCommand.COMMAND_WORD:
            return new ListAmountCommand(); // No Args so no need to Parse.

        case DoneReceiptCommand.COMMAND_WORD:
            return new DoneReceiptCommand();

        case ClearReceiptCommand.COMMAND_WORD:
            return new ClearReceiptCommand();

        case NewReceiptCommand.COMMAND_WORD:
            return new NewReceiptCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
