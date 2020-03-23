package seedu.eylah.expensesplitter.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.util.StringUtil;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String itemName} into a {@code ItemName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemName} is invalid.
     */
    public static ItemName parseItemName(String itemName) throws ParseException {
        requireNonNull(itemName);
        String trimmedItemName = itemName.trim();
        if (!ItemName.isValidName(trimmedItemName)) {
            throw new ParseException(ItemName.MESSAGE_CONSTRAINTS);
        }
        return new ItemName(trimmedItemName);
    }

    /**
     * Parses a {@code String priceInString} into a {@code ItemPrice}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priceInString} is invalid.
     */
    public static ItemPrice parseItemPrice(String priceInString) throws ParseException {
        requireNonNull(priceInString);
        String trimmedPrice = priceInString.trim();
        BigDecimal bigDecimalPrice = new BigDecimal(trimmedPrice);
        if (!ItemPrice.isValidPrice(bigDecimalPrice)) {
            throw new ParseException(ItemPrice.MESSAGE_CONSTRAINTS);
        }
        return new ItemPrice(bigDecimalPrice);
    }

    /**
     * Parses {@code ItemPrice price}, {@code int numPersons} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price}, {@code numPersons} is invalid.
     */
    public static Amount parseAmount(ItemPrice price, int numPersons) throws ParseException {
        requireAllNonNull(price, numPersons);
        BigDecimal bigDecimalNumPersons = new BigDecimal(numPersons);
        BigDecimal itemPrice = price.getItemPrice();
        BigDecimal pricePerPerson = itemPrice.divide(bigDecimalNumPersons,
                new MathContext(4, RoundingMode.HALF_UP));
        return new Amount(pricePerPerson);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code ArrayList<Name>}.
     *
     * @throws ParseException if the given {@code Collection<String> names} is invalid.
     */
    public static ArrayList<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final ArrayList<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(parseName(name));
        }
        return nameList;
    }

    /**
     * Parses {@code ArrayList<Name> names}, {@code ItemPrice price} into a {@code ArrayList<Person>}.
     *
     * @throws ParseException if the given {@code ArrayList<Name> names}, {@code ItemPrice price} is invalid.
     */
    public static ArrayList<Person> parsePersons(ArrayList<Name> names, Amount amount) throws ParseException {
        requireNonNull(names);
        final ArrayList<Person> persons = new ArrayList<>();
        for (Name name : names) {
            persons.add(new Person(name, amount));
        }
        return persons;
    }

    /**
     * Parses a {@code String priceInString} into a {@code ItemPrice}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priceInString} is invalid.
     */
    public static Integer parseIndexV2(String i) {
        requireNonNull(i);
        return Integer.valueOf(i);
    }


}
