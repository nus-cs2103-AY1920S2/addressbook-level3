package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.FindRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.LocationContainsKeywordsPredicate;
import seedu.address.model.restaurant.RNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRestaurantCommand object
 */
public class FindRestaurantCommandParser implements Parser<FindRestaurantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindRestaurantCommand
     * and returns a FindRestaurantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRestaurantCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        ArrayList<String> rNameKeywords = new ArrayList<>();
        ArrayList<String> locationKeywords = new ArrayList<>();

        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("n/")) {
                nameKeywords[i] = nameKeywords[i].substring(2);
                while (!nameKeywords[i].contains("l/") && i != nameKeywords.length) {
                    rNameKeywords.add(nameKeywords[i]);
                    i++;
                    if (i == nameKeywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        for (int i = 0; i < nameKeywords.length; i++) {
            if (nameKeywords[i].contains("l/")) {
                nameKeywords[i] = nameKeywords[i].substring(2);
                while (!nameKeywords[i].contains("n/") && i != nameKeywords.length) {
                    locationKeywords.add(nameKeywords[i]);
                    i++;
                    if (i == nameKeywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        String[] rNameKeywordsArray = new String[rNameKeywords.size()];
        for (int i = 0; i < rNameKeywords.size(); i++) {
            rNameKeywordsArray[i] = rNameKeywords.get(i);
        }

        String[] locationKeywordsArray = new String[locationKeywords.size()];
        for (int i = 0; i < locationKeywords.size(); i++) {
            locationKeywordsArray[i] = locationKeywords.get(i);
        }

        RNameContainsKeywordsPredicate rNamePredicate =
                new RNameContainsKeywordsPredicate(Arrays.asList(rNameKeywordsArray));
        LocationContainsKeywordsPredicate locationPredicate =
                new LocationContainsKeywordsPredicate(Arrays.asList(locationKeywordsArray));

        return new FindRestaurantCommand(rNamePredicate, locationPredicate);

    }

}
