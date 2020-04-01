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


        if (!(argMultimap.getValue(PREFIX_CATEGORY).isPresent() || argMultimap.getValue(PREFIX_MONTH).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER, FilterCommand.MESSAGE_USAGE));
        }

        try {
            if (argMultimap.getValue(PREFIX_CATEGORY).isPresent() && argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = argMultimap.getValue(PREFIX_CATEGORY).get().trim();

                // sends the next word after "date" to see if it matches any transaction dates
                String date = argMultimap.getValue(PREFIX_MONTH).get().trim();
                // removes the day in the string version of transaction date, so we filter by month
                String dateMinusDay;
                if (date.equals("all")) {
                    dateMinusDay = "all";
                } else {
                    dateMinusDay = date.split("-")[0] + "-" + date.split("-")[1];
                }
                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)),
                        new DateEqualsKeywordPredicate(Arrays.asList(dateMinusDay)));
            } else if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                    && !argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = argMultimap.getValue(PREFIX_CATEGORY).get().trim();
                // set date as all transaction dates
                String dateMinusDay = "all";

                return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(cat)),
                        new DateEqualsKeywordPredicate(Arrays.asList(dateMinusDay)));
            } else if (!argMultimap.getValue(PREFIX_CATEGORY).isPresent()
                    && argMultimap.getValue(PREFIX_MONTH).isPresent()) {
                // get category filter
                String cat = "all";

                // sends the next word after "date" to see if it matches any transaction dates
                String date = argMultimap.getValue(PREFIX_MONTH).get().trim();
                // removes the day in the string version of transaction date, so we filter by month
                String dateMinusDay;
                if (date.equals("all")) {
                    dateMinusDay = "all";
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

}
