package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.FindTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodName;
import seedu.address.model.supplier.Name;
import seedu.address.model.transaction.TransactionContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTransactionCommand object
 */
public class FindTransactionCommandParser implements Parser<FindTransactionCommand> {

    /**
     * defines the type of transaction
     */
    public enum TransactionType {
        BUY_TRANSACTION, SELL_TRANSACTION
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindTransactionCommand
     * and returns a FindTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // find transaction by type of transaction, name of supplier, name of good.
        // or any combination of the above
        // if there are multiple input for name, good name, only the last input will be taken.

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_GOOD_NAME);

        // preamble stores the type of transaction
        TransactionType typeOfTransaction = null;
        String preamble = argMultimap.getPreamble();
        switch (preamble.trim()) {
        case "":
            break;
        case "buy":
            typeOfTransaction = TransactionType.BUY_TRANSACTION;
            break;
        case "sell":
            typeOfTransaction = TransactionType.SELL_TRANSACTION;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTransactionCommand.INVALID_TRANSACTION_TYPE));
        }

        // name stores the name of supplier
        Name supplierName = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        // good name stores the name of the good
        GoodName goodName = null;
        if (argMultimap.getValue(PREFIX_GOOD_NAME).isPresent()) {
            goodName = ParserUtil.parseGoodName(argMultimap.getValue(PREFIX_GOOD_NAME).get());
        }

        // at least one field must be provided
        if (typeOfTransaction == null && supplierName == null && goodName == null) {
            throw new ParseException(FindTransactionCommand.MESSAGE_NOT_FIELD_PROVIDED);
        }

        return new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(typeOfTransaction, supplierName, goodName));
    }

}
