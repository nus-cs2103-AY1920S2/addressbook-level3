package seedu.zerotoone.logic.parser.schedule;

import static java.util.Objects.requireNonNull;

import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.schedule.DateTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ScheduleParserUtil extends ParserUtil {

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }
}
