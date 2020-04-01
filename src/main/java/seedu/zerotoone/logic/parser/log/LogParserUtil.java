package seedu.zerotoone.logic.parser.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.schedule.DateTime;

public class LogParserUtil {
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        return LocalDateTime.parse(dateTime.trim());
    }
}
