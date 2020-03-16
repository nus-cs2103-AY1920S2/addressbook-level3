package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_DATE;

import seedu.address.logic.commands.exceptions.BuyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {
    @Override
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_QUANTITY, PREFIX_GOOD_NAME, PREFIX_EXPIRY_DATE, PREFIX_TRANSACTION_DATE);

        // TODO: assert that quantity can be parsed to an integer / doesn't overflow an int
        int quantity;
        try {
            quantity = Integer.parseInt(argMultiMap.getValue(PREFIX_QUANTITY).orElse("bad quantity"));
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE), nfe);
        }

        String supplierName = argMultiMap.getValue(PREFIX_NAME).orElse("bad supplier name");
        String goodName = argMultiMap.getValue(PREFIX_GOOD_NAME).orElse("bad good name");
        String expiryDate = argMultiMap.getValue(PREFIX_EXPIRY_DATE).orElse("bad expiry date");
        String transactionDate = argMultiMap.getValue(PREFIX_TRANSACTION_DATE).orElse("bad transaction date");

        return new BuyCommand(supplierName, goodName, quantity, expiryDate, transactionDate);
    }
}
