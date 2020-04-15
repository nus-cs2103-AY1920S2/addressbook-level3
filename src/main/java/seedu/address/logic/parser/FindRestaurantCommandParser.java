package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_BLANK_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.LocationContainsKeywordsPredicate;
import seedu.address.model.restaurant.RNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRestaurantCommand object
 */
public class FindRestaurantCommandParser implements Parser<FindRestaurantCommand> {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

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

        String[] keywords = trimmedArgs.split("\\s+");

        ArrayList<String> rNameKeywords = new ArrayList<>();
        ArrayList<String> locationKeywords = new ArrayList<>();

        // run checks to make sure there is no invalid command!
        boolean hasRName = false;
        boolean hasLocation = false;
        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("n/")) {
                if (keywords[i].substring(0, 2).equals("n/")) {
                    hasRName = true;
                }
            }
            if (keywords[i].contains("l/")) {
                if (keywords[i].substring(0, 2).equals("l/")) {
                    hasLocation = true;
                }
            }
        }
        if ((hasRName == false) && (hasLocation == false)) {
            // then they did not provide any keywords to search for!
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
        }

        // state check
        logger.info("hasRName: " + String.valueOf(hasRName));
        logger.info("hasLocation: " + String.valueOf(hasLocation));


        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("n/")) {
                keywords[i] = keywords[i].substring(2);
                while (!keywords[i].contains("l/") && i != keywords.length) {
                    rNameKeywords.add(keywords[i]);
                    i++;
                    if (i == keywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        // if they wrote in the prefix but did not supply keywords
        if (hasRName) {
            for (int i = 0; i < rNameKeywords.size(); i++) {
                if (rNameKeywords.get(i).equals("")) {
                    throw new ParseException(
                            String.format(MESSAGE_BLANK_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
                }
            }
        }

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].contains("l/")) {
                keywords[i] = keywords[i].substring(2);
                while (!keywords[i].contains("n/") && i != keywords.length) {
                    locationKeywords.add(keywords[i]);
                    i++;
                    if (i == keywords.length) {
                        break;
                    }
                }
                break;
            }
        }

        // if they wrote in the prefix but did not supply keywords
        if (hasLocation) {
            for (int i = 0; i < locationKeywords.size(); i++) {
                if (locationKeywords.get(i).equals("")) {
                    throw new ParseException(
                            String.format(MESSAGE_BLANK_COMMAND_FORMAT, FindRestaurantCommand.MESSAGE_USAGE));
                }
            }
        }


        // assign the keywords to an Array
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
