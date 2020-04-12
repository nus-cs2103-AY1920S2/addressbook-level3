package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_ITEM;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_NAME;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_PRICE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Parses input arguments and creates a new AddCommandItem object.
 */
public class AddItemCommandParser implements Parser<AddItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemCommand
     * and returns an AddItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddItemCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM, PREFIX_PRICE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        ItemPrice price;

        /*
        if (argMultimap.getValue(PREFIX_PRICE).get().length() >= 5) {
            throw new ParseException(String.format("Amount field"
            + " cannot be more than 5 digits long. "
            + " The maximum price of an item is 9999.",
                AddItemCommand.MESSAGE_USAGE));
        }

         */

        try {
            price = ParserUtil.parseItemPrice(argMultimap.getValue(PREFIX_PRICE).get());

            if (price.getItemPrice().compareTo(new BigDecimal("10000")) == 1) {
                throw new ParseException(String.format("Amount field"
                        + " cannot be more than 10000. "
                        + " The maximum price of an item is 10000",
                    AddItemCommand.MESSAGE_USAGE));
            }

        } catch (NumberFormatException ex) {
            throw new ParseException(String.format("Amount field"
                    + " does not require the '$' sign and can only contain numerical digits. "
                    + "Do enter `help` if you require further clarification.",
                AddItemCommand.MESSAGE_USAGE));
        }


        ItemName itemName = ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM).get());
        ArrayList<Name> names = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        Amount amountPerPerson = ParserUtil.parseAmount(price, names.size());
        ArrayList<Person> persons = ParserUtil.parsePersons(names, amountPerPerson);
        Item item = new Item(itemName, price, amountPerPerson);

        return new AddItemCommand(item, persons, amountPerPerson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
