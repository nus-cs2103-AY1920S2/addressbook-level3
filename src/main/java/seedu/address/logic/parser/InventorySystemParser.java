package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;

import seedu.address.logic.commands.customer.AddCustomerCommand;
import seedu.address.logic.commands.customer.ClearCustomerCommand;
import seedu.address.logic.commands.customer.DeleteCustomerCommand;
import seedu.address.logic.commands.customer.EditCustomerCommand;
import seedu.address.logic.commands.customer.FindCustomerCommand;
import seedu.address.logic.commands.customer.ListCustomerCommand;
import seedu.address.logic.commands.product.AddProductCommand;
import seedu.address.logic.commands.product.ClearProductCommand;
import seedu.address.logic.commands.product.DeleteProductCommand;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.logic.commands.product.FindProductCommand;
import seedu.address.logic.commands.product.ListProductCommand;
import seedu.address.logic.commands.product.LowLimitCommand;
import seedu.address.logic.commands.product.PlotSalesCommand;
import seedu.address.logic.commands.statistics.PredictCommand;
import seedu.address.logic.commands.statistics.ProfitCommand;
import seedu.address.logic.commands.statistics.RevenueCommand;
import seedu.address.logic.commands.transaction.AddTransactionCommand;
import seedu.address.logic.commands.transaction.ClearTransactionCommand;
import seedu.address.logic.commands.transaction.EditTransactionCommand;
import seedu.address.logic.commands.transaction.FindTransactionCommand;
import seedu.address.logic.commands.transaction.ListTransactionCommand;
import seedu.address.logic.commands.transaction.UndoTransactionCommand;

import seedu.address.logic.parser.customer.AddCustomerCommandParser;
import seedu.address.logic.parser.customer.DeleteCustomerCommandParser;
import seedu.address.logic.parser.customer.EditCustomerCommandParser;
import seedu.address.logic.parser.customer.FindCustomerCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.product.AddProductCommandParser;
import seedu.address.logic.parser.product.DeleteProductCommandParser;
import seedu.address.logic.parser.product.EditProductCommandParser;
import seedu.address.logic.parser.product.FindProductCommandParser;
import seedu.address.logic.parser.product.LowLimitCommandParser;
import seedu.address.logic.parser.product.PlotSalesCommandParser;
import seedu.address.logic.parser.statistics.ProfitCommandParser;
import seedu.address.logic.parser.statistics.RevenueCommandParser;
import seedu.address.logic.parser.transaction.AddTransactionCommandParser;
import seedu.address.logic.parser.transaction.EditTransactionCommandParser;
import seedu.address.logic.parser.transaction.FindTransactionCommandParser;
import seedu.address.logic.parser.transaction.UndoTransactionCommandParser;

/**
 * Parses user input.
 */
public class InventorySystemParser {

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

        case AddCustomerCommand.COMMAND_WORD:
            return new AddCustomerCommandParser().parse(arguments);

        case EditCustomerCommand.COMMAND_WORD:
            return new EditCustomerCommandParser().parse(arguments);

        case DeleteCustomerCommand.COMMAND_WORD:
            return new DeleteCustomerCommandParser().parse(arguments);

        case ClearCustomerCommand.COMMAND_WORD:
            return new ClearCustomerCommand();

        case FindCustomerCommand.COMMAND_WORD:
            return new FindCustomerCommandParser().parse(arguments);

        case ListCustomerCommand.COMMAND_WORD:
            return new ListCustomerCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(arguments);

        case ListProductCommand.COMMAND_WORD:
            return new ListProductCommand();

        case ClearProductCommand.COMMAND_WORD:
            return new ClearProductCommand();

        case DeleteProductCommand.COMMAND_WORD:
            return new DeleteProductCommandParser().parse(arguments);

        case EditProductCommand.COMMAND_WORD:
            return new EditProductCommandParser().parse(arguments);

        case FindProductCommand.COMMAND_WORD:
            return new FindProductCommandParser().parse(arguments);

        case LowLimitCommand.COMMAND_WORD:
            return new LowLimitCommandParser().parse(arguments);

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments);

        case EditTransactionCommand.COMMAND_WORD:
            return new EditTransactionCommandParser().parse(arguments);

        case FindTransactionCommand.COMMAND_WORD:
            return new FindTransactionCommandParser().parse(arguments);

        case ListTransactionCommand.COMMAND_WORD:
            return new ListTransactionCommand();

        case UndoTransactionCommand.COMMAND_WORD:
            return new UndoTransactionCommandParser().parse(arguments);

        case ClearTransactionCommand.COMMAND_WORD:
            return new ClearTransactionCommand();

        case RevenueCommand.COMMAND_WORD:
            return new RevenueCommandParser().parse(arguments);

        case ProfitCommand.COMMAND_WORD:
            return new ProfitCommandParser().parse(arguments);

        case PredictCommand.COMMAND_WORD:
            return new PredictCommand();

        case PlotSalesCommand.COMMAND_WORD:
            return new PlotSalesCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
