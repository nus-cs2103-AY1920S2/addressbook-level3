package csdev.couponstash.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Supplier;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.SavedCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * This class contains logic for parsing a
 * given String into a SavedCommand that
 * can be executed by Coupon Stash
 */
public class SavedCommandParser implements Parser<SavedCommand> {

    /**
     * Parses a String (which will be taken from the
     * application when a user types in a command) into
     * the corresponding SavedCommand that it represents
     * @param userInput The String representing raw command
     *                  from the user.
     * @return Returns the corresponding SavedCommand,
     *     if parsing is successful.
     * @throws ParseException If the user's input does
     *     not match the exact format for the "saved"
     *     command (e.g. wrong prefix for the date)
     */
    @Override
    public SavedCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                CliSyntax.PREFIX_DATE,
                CliSyntax.PREFIX_START_DATE,
                CliSyntax.PREFIX_EXPIRY_DATE);
        Optional<String> specificDate = argMultimap.getValue(CliSyntax.PREFIX_DATE);
        Optional<String> startDate = argMultimap.getValue(CliSyntax.PREFIX_START_DATE);
        Optional<String> endDate = argMultimap.getValue(CliSyntax.PREFIX_EXPIRY_DATE);
        Supplier<ParseException> dateRangeException = () ->
                new ParseException(SavedCommand.MESSAGE_ONLY_ONE_DATE_OF_RANGE);

        if (specificDate.isPresent() && (startDate.isPresent() || endDate.isPresent())) {
            // parser does not know whether to use a range or a single date
            throw dateRangeException.get();
        } else if (specificDate.isPresent()) {
            return getSavedCommandForSpecificDate(specificDate.get());
        } else if (startDate.isPresent() || endDate.isPresent()) {
            String strStart = startDate.orElseThrow(dateRangeException);
            String strEnd = endDate.orElseThrow(dateRangeException);
            return getCommandForRangeOfDates(strStart, strEnd);
        } else if (userInput.isBlank()) {
            // no dates provided
            return new SavedCommand();
        } else {
            throw new ParseException(SavedCommand.MESSAGE_UNEXPECTED_EXTRA_WORDS);
        }
    }

    /**
     * An utility method to attempt to parse only one String, if
     * it was determined that the user wants to find out savings
     * only on a single date. If parsing of this single date is
     * successful, the appropriate SavedCommand is returned.
     *
     * @param specificDate The String containing user input
     *                     of the specific date to see the
     *                     total savings of.
     * @return Returns SavedCommand if the date is parsed successfully.
     * @throws ParseException If parsing of String to date fails.
     */
    private SavedCommand getSavedCommandForSpecificDate(String specificDate) throws ParseException {
        LocalDate specDate;
        try {
            specDate = DateUtil.parseStringToDate(specificDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(DateUtil.MESSAGE_DATE_WRONG_FORMAT);
        }
        return new SavedCommand(specDate);
    }

    /**
     * An utility method to attempt to parse two Strings given
     * for start date and end date. If parsing of these two
     * Strings are successful, the SavedCommand is returned.
     *
     * @param strStart The String containing start date of the
     *                 time period in which to check Savings.
     * @param strEnd The String containing end date of the
     *               time period in which to check Savings.
     * @return Returns SavedCommand, if all goes well.
     * @throws ParseException If the parsing of the Strings to
     *                        dates fails, or if the start date
     *                        is not before the end date.
     */
    private SavedCommand getCommandForRangeOfDates(String strStart, String strEnd) throws ParseException {
        LocalDate sDate;
        LocalDate eDate;
        try {
            sDate = DateUtil.parseStringToDate(strStart);
            eDate = DateUtil.parseStringToDate(strEnd);
        } catch (DateTimeParseException e) {
            throw new ParseException(DateUtil.MESSAGE_DATE_WRONG_FORMAT);
        }
        if (sDate.isAfter(eDate) || sDate.equals(eDate)) {
            throw new ParseException(SavedCommand.MESSAGE_INVALID_DATE_RANGE);
        }
        return new SavedCommand(sDate, eDate);
    }
}
