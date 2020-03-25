package seedu.address.logic.parser.product;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THRESHOLD;
import static seedu.address.model.util.QuantityThreshold.DEFAULT_VALUE;

import java.util.stream.Stream;

import seedu.address.logic.commands.product.AddProductCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.Sales;
import seedu.address.model.util.Description;
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
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_QUANTITY, PREFIX_SALES);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Sales sales;
        if (arePrefixesPresent(argMultimap, PREFIX_SALES)) {
            sales = ParserUtil.parseSales(argMultimap.getValue(PREFIX_SALES).get());
        } else {
            sales = new Sales("0");
        }
        QuantityThreshold threshold;
        if (arePrefixesPresent(argMultimap, PREFIX_THRESHOLD)) {
            threshold = ParserUtil.parseThreshold(argMultimap.getValue(PREFIX_THRESHOLD).get());
        } else {
            threshold = new QuantityThreshold(DEFAULT_VALUE);
        }

        Product product = new Product(description, price, quantity, sales, threshold);

        return new AddProductCommand(product);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

