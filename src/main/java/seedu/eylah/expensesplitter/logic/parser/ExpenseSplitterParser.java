package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.parser.CommonParser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.logic.commands.BackCommand;
import seedu.eylah.expensesplitter.logic.commands.ClearReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.DeleteItemCommand;
import seedu.eylah.expensesplitter.logic.commands.DoneReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.ExitCommand;
import seedu.eylah.expensesplitter.logic.commands.HelpCommand;
import seedu.eylah.expensesplitter.logic.commands.ListAmountCommand;
import seedu.eylah.expensesplitter.logic.commands.ListReceiptCommand;
import seedu.eylah.expensesplitter.logic.commands.PaidCommand;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Parses user input for ExpenseSplitter.
 */
public class ExpenseSplitterParser implements CommonParser<SplitterModel> {

    @Override
    public Command<SplitterModel> parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");



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

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
