package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_FILTER;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_MONTH;

import java.util.Arrays;

import seedu.expensela.logic.commands.FilterCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_CATEGORY);

        // missing both category and month
        if (!(argMultimap.getValue(PREFIX_CATEGORY).isPresent() || argMultimap.getValue(PREFIX_MONTH).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER, FilterCommand.MESSAGE_USAGE));
        }

        // invalid category
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                && !isValidCategory(argMultimap.getValue(PREFIX_CATEGORY).get().trim().toUpperCase())) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER, FilterCommand.MESSAGE_USAGE));
        }

        // invalid month
        if (argMultimap.getValue(PREFIX_MONTH).isPresent()
                && !isValidMonth(argMultimap.getValue(PREFIX_MONTH).get().trim())) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER, FilterCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_CATEGORY).isPresent() && argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = argMultimap.getValue(PREFIX_CATEGORY).get().trim().toUpperCase();

                // sends the next word after "date" to see if it matches any transaction dates
                String date = argMultimap.getValue(PREFIX_MONTH).get().trim();
                // removes the day in the string version of transaction date, so we filter by month
                String dateMinusDay;
                if (date.toUpperCase().equals("ALL")) {
                    dateMinusDay = "ALL";
                } else {
                    dateMinusDay = date.split("-")[0] + "-" + date.split("-")[1];
                }
                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)),
                        new DateEqualsKeywordPredicate(Arrays.asList(dateMinusDay)));
            } else if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                    && !argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = argMultimap.getValue(PREFIX_CATEGORY).get().trim().toUpperCase();
                // set date as all transaction dates
                String dateMinusDay = "ALL";

                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)),
                        new DateEqualsKeywordPredicate(Arrays.asList(dateMinusDay)));
            } else if (!argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                    && argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = "ALL";

                // sends the next word after "date" to see if it matches any transaction dates
                String date = argMultimap.getValue(PREFIX_MONTH).get().trim();
                // removes the day in the string version of transaction date, so we filter by month
                String dateMinusDay;
                if (date.toUpperCase().equals("ALL")) {
                    dateMinusDay = "ALL";
                } else {
                    dateMinusDay = date.split("-")[0] + "-" + date.split("-")[1];
                }
                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)),
                        new DateEqualsKeywordPredicate(Arrays.asList(dateMinusDay)));
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /**
     * Checks if the category input by the user is valid
     * @param input
     * @return
     */
    public boolean isValidCategory(String input) {
        switch(input) {
        case("FOOD"):
            return true;
        case("SHOPPING"):
            return true;
        case("TRANSPORT"):
            return true;
        case("GROCERIES"):
            return true;
        case("HEALTH"):
            return true;
        case("RECREATION"):
            return true;
        case("MISC"):
            return true;
        case("UTILITIES"):
            return true;
        case("INCOME"):
            return true;
        case("ALL"):
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks if the month inputted by the user is valid.
     * @param input
     * @return
     */
    public boolean isValidMonth(String input) {
        try {
            if (input.equals("ALL")) {
                return true;
            }
            String inputYear = input.split("-")[0];
            int inputYearInteger = Integer.parseInt(inputYear);
            String inputMonth = input.split("-")[1];
            int inputMonthInteger = Integer.parseInt(inputMonth);
            if (inputYear.length() != 4) {
                return false;
            }
            if (inputYearInteger < 1900) {
                return false;
            }
            if (inputMonth == null) {
                return false;
            }
            if (inputMonth.length() != 2) {
                return false;
            }
            if (inputMonthInteger < 1 || inputMonthInteger > 12) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
