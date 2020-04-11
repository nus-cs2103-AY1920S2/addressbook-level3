package seedu.zerotoone.logic.parser.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class LogParserUtilTest {

    @Test
    void parseDateTime_null_nullPointerException() {
        assertThrows(NullPointerException.class, () -> LogParserUtil.parseDateTime(null));
    }

    @Test
    void parseDateTime_invalid_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, () -> LogParserUtil.parseDateTime("INVALID DATE"));
    }

    @Test
    void parseDateTime() {
        LocalDateTime datetime = LocalDateTime.of(1996, Month.JULY, 24, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        assertEquals(datetime, LogParserUtil.parseDateTime(datetime.format(formatter)));
    }
}
