package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.task.TaskSearchCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskSearchPredicate;

/**
 * Parses input arguments and creates a new TaskSearchCommand object
 */
public class TaskSearchCommandParser implements Parser<TaskSearchCommand> {

    public static final String DATE_STRING = "date";
    public static final String MONTH_STRING = "month";
    public static final String YEAR_STRING = "year";

    private static final int MINIMUM_YEAR = 1;
    private static final int MAXIMUM_YEAR = 9999;
    private static final int MINIMUM_MONTH = 1;
    private static final int MAXIMUM_MONTH = 12;
    private static final int MINIMUM_DATE = 1;
    private static final int MAXIMUM_DATE = 31;

    private static final int THIRTY_MAXIMUM_DATE = 30;
    private static final int TWENTY_NINE_MAXIMUM_DATE = 29;
    private static final int TWENTY_EIGHT_MAXIMUM_DATE = 28;

    private static final int FEBRUARY = 2;

    private static final List<Integer> THIRTY_DAY_MONTHS = new ArrayList<Integer>(Arrays.asList(4, 6, 9, 11));

    private static final ParseException INVALID_PARAMETERS_EXCEPTION =
            new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TaskSearchCommand.MESSAGE_INVALID_DAY_MONTH_YEAR));

    private static final ParseException OUT_OF_BOUNDS_PARAMETERS_EXCEPTION =
            new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TaskSearchCommand.MESSAGE_OUT_OF_BOUNDS_VALUES));
    /**
     * Parses the given {@code String} of arguments in the context of the TaskSearchCommand
     * and returns a TaskSearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskSearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_DATE, PREFIX_MONTH, PREFIX_YEAR);

        HashMap<String, Integer> keywords = new HashMap<String, Integer>();

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
            && !arePrefixesPresent(argMultimap, PREFIX_MONTH)
                && !arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskSearchCommand.MESSAGE_USAGE));
        }

        if (argMultimap.numOfValuesPresent(PREFIX_DATE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DATE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_MONTH) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MONTH));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_YEAR) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_YEAR));
        }

        // check for invalid date, month, and/or year, such as strings and characters

        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            int day;
            try {
                day = Integer.parseInt(argMultimap.getValue(PREFIX_DATE));
            } catch (NumberFormatException error) {
                throw INVALID_PARAMETERS_EXCEPTION;
            }
            keywords.put(DATE_STRING, day);
        }

        assert (keywords.size() <= 1) : "Only the date key has been inserted";

        if (arePrefixesPresent(argMultimap, PREFIX_MONTH)) {
            int month;
            try {
                month = Integer.parseInt(argMultimap.getValue(PREFIX_MONTH));
            } catch (NumberFormatException error) {
                throw INVALID_PARAMETERS_EXCEPTION;
            }
            keywords.put(MONTH_STRING, month);
        }

        assert (keywords.size() <= 2) : "Only the date and month key has been inserted";

        if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            int year;
            try {
                year = Integer.parseInt(argMultimap.getValue(PREFIX_YEAR));
            } catch (NumberFormatException error) {
                throw INVALID_PARAMETERS_EXCEPTION;
            }
            keywords.put(YEAR_STRING, year);
        }

        assert (keywords.size() <= 3) : "Only three keys date, month, and year can be inserted";

        // check for out of bounds date, month, and/or year

        if (isAllDateMonthYearProvided(keywords)) {
            if (!isValidDate(keywords)) {
                throw OUT_OF_BOUNDS_PARAMETERS_EXCEPTION;
            } else {
                return new TaskSearchCommand(new TaskSearchPredicate(keywords));
            }
        }

        assert (keywords.size() <= 2) : "At least one of the three parameters should be missing";

        boolean isOneParameterProvided = (keywords.size() == 1);

        if (isOneParameterProvided) {
            if (!isTheOnlyKeyNotOutOfBound(keywords)) {
                throw OUT_OF_BOUNDS_PARAMETERS_EXCEPTION;
            } else {
                return new TaskSearchCommand(new TaskSearchPredicate(keywords));
            }
        }

        assert (keywords.size() == 2) : "There should be two out of three parameters provided at this point";
        String missingKeyword = findMissingKeyword(keywords);

        if (missingKeyword.equals(YEAR_STRING)) { // dd/MM, painful combination to check
            if (!isCompatibleDateMonth(keywords.get(DATE_STRING), keywords.get(MONTH_STRING))) {
                throw OUT_OF_BOUNDS_PARAMETERS_EXCEPTION;
            } else {
                return new TaskSearchCommand(new TaskSearchPredicate(keywords));
            }
        }

        if (!isCombinationValidForIndependentParameters(keywords)) { // dd/yy or MM/yy
            throw OUT_OF_BOUNDS_PARAMETERS_EXCEPTION;
        } else {
            return new TaskSearchCommand(new TaskSearchPredicate(keywords));
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix) != null);
    }

    /**
     * Returns true if {@code year} is a leap year
     */
    private boolean isLeapYear(int year) {
        boolean isLeapYearMultipleOf400 = (year % 400 == 0);
        boolean isYearNotMultipleOf100 = !(year % 100 == 0);
        boolean isYearMultipleOf4 = (year % 4 == 0);
        boolean isLeapYearMultipleOf4AndNot100 = isYearMultipleOf4 && isYearNotMultipleOf100;

        return (isLeapYearMultipleOf400 || isLeapYearMultipleOf4AndNot100);
    }

    /**
     * Returns true if the date {@code date}/{@code month}/{@code year} is valid
     */
    private boolean isValidDate(HashMap<String, Integer> keywords) {
        int date = keywords.get(DATE_STRING);
        int month = keywords.get(MONTH_STRING);
        int year = keywords.get(YEAR_STRING);

        if (isOutOfBoundsYear(year)) {
            return false;
        }

        if (isOutOfBoundsMonth(month)) {
            return false;
        }

        if (isOutOfBoundsDate(date)) {
            return false;
        }

        if (isMonthThirtyDays(month) && isOutOfBoundsDateThirtyDaysMonth(date)) {
            return false;
        }

        if (isLeapYear(year)) {
            if (isOutOfBoundsDateInFebruaryLeapYear(date)) {
                return false;
            }
        } else {
            if (isOutOfBoundsDateInFebruaryNoLeapYear(date)) {
                return false;
            }
        }

        return true;
    }

    private boolean isOutOfBoundsYear(int year) {
        return !(MINIMUM_YEAR <= year && year <= MAXIMUM_YEAR);
    }

    private boolean isOutOfBoundsMonth(int month) {
        return !(MINIMUM_MONTH <= month && month <= MAXIMUM_MONTH);
    }

    private boolean isOutOfBoundsDate(int date) {
        return !(MINIMUM_DATE <= date && date <= MAXIMUM_DATE);
    }

    /**
     * Checks if the {@code keywords}  make up a valid date, such as {@code 24/04/2020}.
     * Precondition: {@code keywords} contains all of date, month, and year
     */
    private static boolean isAllDateMonthYearProvided(HashMap<String, Integer> keywords) {
        List<String> allKeywords = new ArrayList<String>();
        allKeywords.add(DATE_STRING);
        allKeywords.add(MONTH_STRING);
        allKeywords.add(YEAR_STRING);

        assert (allKeywords.size() == 3) : "Maximum is three parameters for search";
        return (keywords.size() == allKeywords.size());
    }

    /**
     * Checks if the only value in {@code keywords} is valid.
     * Precondition: {@code keywords} contains only ONE of date, month, and year,
     * and we need to check if that corresponding value is valid.
     */
    private boolean isTheOnlyKeyNotOutOfBound(HashMap<String, Integer> keywords) {
        assert (keywords.size() == 1) : "There should only be one parameter out of date, month, year";

        if (keywords.containsKey(DATE_STRING)) {
            int date = keywords.get(DATE_STRING);
            if (isOutOfBoundsDate(date)) {
                return false;
            }
        }

        if (keywords.containsKey(MONTH_STRING)) {
            int month = keywords.get(MONTH_STRING);
            if (isOutOfBoundsMonth(month)) {
                return false;
            }
        }

        if (keywords.containsKey(YEAR_STRING)) {
            int year = keywords.get(YEAR_STRING);
            if (isOutOfBoundsYear(year)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the ONE missing {@code keyword} in {@code keywords}.
     * Precondition: {@code keywords} contains exactly TWO of date, month, and year.
     */
    private String findMissingKeyword(HashMap<String, Integer> keywords) {
        assert (keywords.size() == 2) : "There should only be one keyword missing";

        if (!keywords.containsKey(DATE_STRING)) {
            return DATE_STRING;
        }

        if (!keywords.containsKey(MONTH_STRING)) {
            return MONTH_STRING;
        }

        return YEAR_STRING;
    }
    private boolean isOutOfBoundsDateInFebruaryNoLeapYear(int date) {
        return !(MINIMUM_DATE <= date && date <= TWENTY_EIGHT_MAXIMUM_DATE);
    }

    private boolean isOutOfBoundsDateInFebruaryLeapYear(int date) {
        return !(MINIMUM_DATE <= date && date <= TWENTY_NINE_MAXIMUM_DATE);
    }

    private boolean isOutOfBoundsDateThirtyDaysMonth(int date) {
        return !(MINIMUM_DATE <= date && date <= THIRTY_MAXIMUM_DATE);
    }

    private boolean isMonthFebruary(int month) {
        assert !isOutOfBoundsMonth(month) : "The month provided should be valid and in bounds";
        return month == FEBRUARY;
    }

    private boolean isMonthThirtyDays(int month) {
        assert !isOutOfBoundsMonth(month) : "The month provided should be valid and in bounds";
        return THIRTY_DAY_MONTHS.contains(month);
    }

    /**
     * Checks if the provided {@code date} and {@code month} makes up a valid date.
     * @return
     */
    private boolean isCompatibleDateMonth(int date, int month) {
        if (isOutOfBoundsMonth(month)) {
            return false;
        }

        if (isMonthFebruary(month)) {
            if (isOutOfBoundsDateInFebruaryLeapYear(date)) {
                return false;
            }
        } else if (isMonthThirtyDays(month)) { // thirty day months
            if (isOutOfBoundsDateThirtyDaysMonth(date)) {
                return false;
            }
        } else {
            if (isOutOfBoundsDate(date)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the combination {@dd/YY} or {@mm/YY} is valid.
     * Note that we can independently check date, month, and year in this case.
     */
    private boolean isCombinationValidForIndependentParameters(HashMap<String, Integer> keywords) {
        assert (keywords.size() == 2) : "keywords should be dd/yy or MM/yy, which can be independently checked";
        assert (keywords.containsKey(YEAR_STRING)) : "Year should be independently with date only or month only";

        if (isOutOfBoundsYear(keywords.get(YEAR_STRING))) {
            return false;
        }

        if (keywords.containsKey(DATE_STRING)) {
            if (isOutOfBoundsDate(keywords.get(DATE_STRING))) {
                return false;
            }
        }

        if (keywords.containsKey(MONTH_STRING)) {
            if (isOutOfBoundsDate(keywords.get(MONTH_STRING))) {
                return false;
            }
        }
        return true;
    }
}
