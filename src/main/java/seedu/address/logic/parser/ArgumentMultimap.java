package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    public static final String NOT_SINGULAR = "Can only have one argument in the prefix \"%1$s\".";

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
     * If value does not exist, returns insert comment
     */
    public Optional<String> getValue(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            switch (prefix.getPrefix()) {
            case "a/":
                Optional<String> missingAddressString = Optional.of("Insert address here!");
                return missingAddressString;
            case "p/":
                Optional<String> missingPhoneNumberString = Optional.of("Insert phone number here!");
                return missingPhoneNumberString;
            case "e/":
                Optional<String> missingEmailString = Optional.of("Insert email here!");
                return missingEmailString;
            case "nok/":
                Optional<String> missingNokString = Optional.of("Insert NOK details here!");
                return missingNokString;
            case ("temp/"):
                Optional<String> missingTemperatureString = Optional.of("Insert temperature here!");
                return missingTemperatureString;
            case ("att/"):
                Optional<String> missingAttendanceString = Optional.of("Present");
                return missingAttendanceString;
            default:
                List<String> values = getAllValues(prefix);
                return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
            }

        }
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    public Optional<String> getSingleValue(Prefix prefix) throws ParseException {
        List<String> values = getAllValues(prefix);
        if (values.size() > 1) {
            throw new ParseException(String.format(NOT_SINGULAR, prefix.getPrefix()));
        }
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(0));
    }

    /**
     * Returns the last value of {@code prefix}.
     * If value does not exist, returns null.
     */
    public Optional<String> getValueOptional(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return Optional.empty();
        } else {
            List<String> values = getAllValues(prefix);
            return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
        }
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
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble(String prefix) {
        return getValue(new Prefix(prefix)).orElse("");
    }
}
