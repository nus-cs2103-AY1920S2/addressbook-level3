package seedu.address.logic.parser;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.customer.AddCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.Money;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.util.Quantity;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCIPTION;


public class AddTransactionCommandParser implements Parser<AddTransactionCommand>{

    @Override
    public AddTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_QUANTITY,
                        PREFIX_MONEY, PREFIX_TRANS_DESCIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_QUANTITY,
                PREFIX_MONEY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));
        }

        String customer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_CUSTOMER).get());
        String product = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get());
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Money money = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get());

        String transDescription;
        if (arePrefixesPresent(argMultimap, PREFIX_TRANS_DESCIPTION)) {
            transDescription = ParserUtil.parseTransDescription(argMultimap.getValue(PREFIX_TRANS_DESCIPTION).get());
        } else {
            transDescription = "";
        }
        Transaction transaction = new Transaction(customer, product, dateTime, quantity, money, transDescription);

        return new AddTransactionCommand(transaction);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
