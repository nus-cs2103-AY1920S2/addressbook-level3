package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.transaction.AddTransactionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.TransactionFactory;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;

/**
 * Parse input arguments and creates an AddTransactionCommand.
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    @Override
    public AddTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_QUANTITY,
                        PREFIX_MONEY, PREFIX_TRANS_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        if (anyPrefixesDuplicate(argMultimap, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_QUANTITY,
                PREFIX_MONEY, PREFIX_TRANS_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_MULTIPLE_SAME_PREFIX,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        Index customerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CUSTOMER).get());
        Index productIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PRODUCT).get());
        Quantity quantity = ParserUtil.parseTransactionQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        DateTime dateTime = getDateTime(argMultimap);
        Money money = getMoney(argMultimap);
        Description description = getDescription(argMultimap);

        TransactionFactory transactionFactory = new TransactionFactory(customerIndex, productIndex, dateTime,
                quantity, money, description);

        return new AddTransactionCommand(transactionFactory);
    }

    private DateTime getDateTime(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_DATETIME)) {
            return ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        } else {
            return new DateTime(DateTime.DEFAULT_VALUE);
        }
    }

    private Money getMoney(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_MONEY)) {
            return ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get());
        } else {
            return new Money(Money.DEFAULT_VALUE);
        }
    }

    private Description getDescription(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_TRANS_DESCRIPTION)) {
            return ParserUtil.parseTransDescription(argMultimap.getValue(PREFIX_TRANS_DESCRIPTION).get());
        } else {
            return new Description(Description.DEFAULT_VALUE);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesDuplicate(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.hasDuplicateValues(prefix));
    }
}
