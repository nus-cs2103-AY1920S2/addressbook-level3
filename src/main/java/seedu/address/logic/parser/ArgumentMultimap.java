package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /**
     * Prefixes mapped to their respective arguments
     **/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    /**
     * A getter method to get all the values of the ArgumentMultimap.
     *
     * @return Returns a List of strings containing all the values of the ArgumentMultimap.
     */
    public List<String> getAllPrefixValues() {
        List<String> allPrefixValues = new ArrayList<>();
        Collection<List<String>> collectionOfValues = this.argMultimap.values();
        for (List<String> perPrefixValues : collectionOfValues) {
            allPrefixValues.addAll(perPrefixValues);
        }
        return allPrefixValues;
    }

    public boolean getHasTid() {
        return argMultimap.containsKey(PREFIX_TID);
    }

    public boolean getHasName() {
        return argMultimap.containsKey(PREFIX_NAME);
    }

    public boolean getHasPhone() {
        return argMultimap.containsKey(PREFIX_PHONE);
    }

    public boolean getHasAddress() {
        return argMultimap.containsKey(PREFIX_ADDRESS);
    }

    public boolean getHasDeliveryTimeStamp() {
        return argMultimap.containsKey(PREFIX_DELIVERY_TIMESTAMP);
    }

    public boolean getHasReturnTimeStamp() {
        return argMultimap.containsKey(PREFIX_RETURN_TIMESTAMP);
    }

    public boolean getHasWarehouse() {
        return argMultimap.containsKey(PREFIX_WAREHOUSE);
    }

    public boolean getHasCod() {
        return argMultimap.containsKey(PREFIX_COD);
    }

    public boolean getHasComment() {
        return argMultimap.containsKey(PREFIX_COMMENT);
    }

    public boolean getHasItemType() {
        return argMultimap.containsKey(PREFIX_TYPE);
    }

    public boolean getHasEmail() {
        return argMultimap.containsKey(PREFIX_EMAIL);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
            || (obj instanceof ArgumentMultimap
            && this.argMultimap.keySet().equals(((ArgumentMultimap) obj).argMultimap.keySet()));
    }
}
