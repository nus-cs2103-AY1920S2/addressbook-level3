package seedu.eylah.expensesplitter.logic.parser;

import static seedu.eylah.expensesplitter.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.eylah.expensesplitter.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.eylah.expensesplitter.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.eylah.expensesplitter.model.Entry;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;
import seedu.eylah.expensesplitter.model.person.Person;


public class AddItemCommandParser implements Parser<AddItemCommand> {

    public AddItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM, PREFIX_PRICE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        ItemName itemName = ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM).get());
        ItemPrice price = ParserUtil.parseItemPrice(argMultimap.getValue(PREFIX_PRICE).get());
        ArrayList<Name> names = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        ArrayList<Person> persons = ParserUtil.parsePersons(names, price);
        Item item = new Item(itemName, price);

        return new AddItemCommand(item, persons);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
