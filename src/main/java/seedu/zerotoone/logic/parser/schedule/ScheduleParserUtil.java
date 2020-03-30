package seedu.zerotoone.logic.parser.schedule;

import static java.util.Objects.requireNonNull;

import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.schedule.DateTime;

/**
 * STEPH_TODO_JAVADOC
 */
public class ScheduleParserUtil extends ParserUtil {

    /**
     * STEPH_TODO_JAVADOC
     * @param dateTime STEPH_TODO_JAVADOC
     * @return STEPH_TODO_JAVADOC
     * @throws ParseException STEPH_TODO_JAVADOC
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
