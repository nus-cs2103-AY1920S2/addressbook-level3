package seedu.address.logic.parser.product;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INITIAL_QUANTITY;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COSTPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THRESHOLD;

import java.util.stream.Stream;

import seedu.address.logic.commands.product.AddProductCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;

/**
 * Parses input arguments and creates a new AddProductCommand object
 */
public class AddProductCommandParser implements Parser<AddProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProductCommand
     * and returns an AddProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_COSTPRICE,
                        PREFIX_PRICE, PREFIX_QUANTITY, PREFIX_SALES);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_COSTPRICE, PREFIX_PRICE, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE));
        }

        if (anyPrefixesDuplicate(argMultimap, PREFIX_DESCRIPTION, PREFIX_COSTPRICE,
                PREFIX_PRICE, PREFIX_QUANTITY, PREFIX_SALES)) {
            throw new ParseException(String.format(MESSAGE_MULTIPLE_SAME_PREFIX,
                    AddProductCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        CostPrice costPrice = ParserUtil.parseCostPrice(argMultimap.getValue(PREFIX_COSTPRICE).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Quantity quantity = ParserUtil.parseProductQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Money sales = getSales(argMultimap);
        QuantityThreshold threshold = getThreshold(argMultimap, quantity);

        if (quantity.getValue() == 0) {
            throw new ParseException(MESSAGE_INVALID_INITIAL_QUANTITY);
        }

        Product product = new Product(description, costPrice, price, quantity, sales, threshold, 1);

        return new AddProductCommand(product);
    }

    private Money getSales(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_SALES)) {
            return ParserUtil.parseMoney(argMultimap.getValue(PREFIX_SALES).get());
        } else {
            return new Money(Money.DEFAULT_VALUE);
        }
    }

    private QuantityThreshold getThreshold(ArgumentMultimap argMultimap, Quantity quantity)
        throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_THRESHOLD)) {
            return ParserUtil.parseThreshold(argMultimap.getValue(PREFIX_THRESHOLD).get());
        } else {
            // sets the default threshold at 20% of initial quantity
            String lowLimit = String.valueOf(quantity.getValue() / 5);
            return new QuantityThreshold(lowLimit);
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

