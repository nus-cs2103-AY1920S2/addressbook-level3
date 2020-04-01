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
        ArgumentMultimap argMultimap =
                 ArgumentTokenizer.tokenize(
                         userInput,
                         CliSyntax.PREFIX_DATE,
                         CliSyntax.PREFIX_START_DATE,
                         CliSyntax.PREFIX_EXPIRY_DATE);
        Optional<String> specificDate = argMultimap.getValue(CliSyntax.PREFIX_DATE);
        Optional<String> startDate = argMultimap.getValue(CliSyntax.PREFIX_START_DATE);
        Optional<String> endDate = argMultimap.getValue(CliSyntax.PREFIX_EXPIRY_DATE);
        Supplier<ParseException> dateRangeException = () ->
            new ParseException(SavedCommand.MESSAGE_ONLY_ONE_DATE_OF_RANGE);
        if (specificDate.isPresent()) {
            if (startDate.isPresent() || endDate.isPresent()) {
                throw dateRangeException.get();
            }
            LocalDate specDate;
            try {
                specDate = DateUtil.parseStringToDate(specificDate.get());
            } catch (DateTimeParseException e) {
                throw new ParseException(DateUtil.MESSAGE_DATE_WRONG_FORMAT);
            }
            return new SavedCommand(specDate);
        } else if (startDate.isPresent() || endDate.isPresent()) {
            String strStart = startDate.orElseThrow(dateRangeException);
            String strEnd = endDate.orElseThrow(dateRangeException);
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
        } else if (userInput.isBlank()) {
            // no dates provided
            return new SavedCommand();
        } else {
            throw new ParseException(SavedCommand.MESSAGE_UNEXPECTED_EXTRA_WORDS);
        }
    }
}
