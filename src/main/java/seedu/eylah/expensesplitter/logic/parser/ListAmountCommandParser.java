package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.expensesplitter.logic.commands.ListAmountCommand;
import seedu.eylah.expensesplitter.logic.commands.ListReceiptCommand;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAmountCommand object.
 */
public class ListAmountCommandParser implements Parser<ListAmountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListReceiptCommand
     * and returns an ListReceiptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListAmountCommand parse(String args) throws ParseException {

        if (args.equals("listamount")) {
            return new ListAmountCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListReceiptCommand.MESSAGE_USAGE));
        }

    }
}
