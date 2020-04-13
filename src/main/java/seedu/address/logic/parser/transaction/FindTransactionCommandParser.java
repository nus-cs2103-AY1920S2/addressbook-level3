package seedu.address.logic.parser.transaction;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.transaction.FindTransactionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.CustomerContainsKeywordPredicate;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.DateTimeEqualsPredicate;
import seedu.address.model.transaction.JointTransactionPredicate;
import seedu.address.model.transaction.MoneyEqualsPredicate;
import seedu.address.model.transaction.ProductContainsKeywordPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Money;

/**
 * Parse input arguments and creates a new FindCommand object.
 */
public class FindTransactionCommandParser implements Parser<FindTransactionCommand> {

    private final List<Predicate<Transaction>> predicates = new ArrayList<>();

    /**
     * Parses the given {@code String} of arguments in the context of the FindTransactionCommand
     * and returns a FindTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_MONEY);

        if (!anyPrefixesPresent(argMultimap, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_MONEY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTransactionCommand.MESSAGE_USAGE));
        }

        if (anyPrefixesDuplicate(argMultimap, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME, PREFIX_MONEY)) {
            throw new ParseException(String.format(MESSAGE_MULTIPLE_SAME_PREFIX,
                    FindTransactionCommand.MESSAGE_USAGE));
        }

        addToPredicates(argMultimap);

        return new FindTransactionCommand(new JointTransactionPredicate(predicates));
    }

    /**
     * Add attributes entered by user to predicates list.
     * @param argMultimap
     * @throws ParseException
     */
    private void addToPredicates(ArgumentMultimap argMultimap) throws ParseException {
        if (anyPrefixesPresent(argMultimap, PREFIX_CUSTOMER)) {
            String customerArgs = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_CUSTOMER).get()).trim();
            String[] customerKeywords = customerArgs.split("\\s+");
            predicates.add(new CustomerContainsKeywordPredicate(Arrays.asList(customerKeywords)));
        }
        if (anyPrefixesPresent(argMultimap, PREFIX_PRODUCT)) {
            String productArgs = ParserUtil.parseProduct(argMultimap.getValue(PREFIX_PRODUCT).get()).trim();
            String[] productKeywords = productArgs.split("\\s+");
            predicates.add(new ProductContainsKeywordPredicate(Arrays.asList(productKeywords)));
        }
        if (anyPrefixesPresent(argMultimap, PREFIX_DATETIME)) {
            DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            predicates.add(new DateTimeEqualsPredicate(dateTime));
        }
        if (anyPrefixesPresent(argMultimap, PREFIX_MONEY)) {
            Money money = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get());
            predicates.add(new MoneyEqualsPredicate(money));
        }
    }

    /**
     * Returns true if any of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesDuplicate(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.hasDuplicateValues(prefix));
    }

}
