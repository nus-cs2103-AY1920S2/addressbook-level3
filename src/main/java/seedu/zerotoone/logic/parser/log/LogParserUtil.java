package seedu.zerotoone.logic.parser.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * The type Log parser util.
 */
public class LogParserUtil {
    /**
     * Parse date time local date time.
     *
     * @param dateTime the date time
     * @return the local date time
     * @throws DateTimeParseException the date time parse exception
     */
    public static LocalDateTime parseDateTime(String dateTime) throws DateTimeParseException {
        requireNonNull(dateTime);
        return LocalDateTime.parse(dateTime.trim());
    }
}
