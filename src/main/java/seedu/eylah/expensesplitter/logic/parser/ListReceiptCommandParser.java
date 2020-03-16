package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;
import seedu.eylah.expensesplitter.logic.commands.ListReceiptCommand;

/**
 * Parses input arguments and creates a new ListReceiptCommand object
 */
public class ListReceiptCommandParser implements Parser<ListReceiptCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the ListReceiptCommand
     * and returns an ListReceiptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListReceiptCommand parse(String args) throws ParseException {

        if (args.equals("listreceipt")) {
            return new ListReceiptCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListReceiptCommand.MESSAGE_USAGE));
        }

    }
}
