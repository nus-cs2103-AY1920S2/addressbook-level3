package seedu.zerotoone.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

class DateViewUtilTest {
    private Duration mockDuration = Duration.ofHours(1).plusMinutes(2).plusSeconds(3);
    private LocalDateTime mockTime = LocalDateTime.of(1996, Month.JULY, 24, 8, 0);

    @Test
    void getPrettyDateTime() {
        String actual = DateViewUtil.getPrettyDateTime(mockTime);
        assertEquals("Wed 24 July 1996,  08:00", actual);
    }

    @Test
    void getPrettyDateRangeDateTime() {
        String actual = DateViewUtil.getPrettyDateRangeDateTime(mockTime, mockTime.plusMinutes(10));
        assertEquals("Wed 24 July 1996,  08:00 - 08:10", actual);

        actual = DateViewUtil.getPrettyDateRangeDateTime(mockTime, mockTime.plusDays(1));
        assertEquals("24 July 1996, 08:00, Wed - 25 July 1996, 08:00, Thu", actual);
    }

    @Test
    void getPrettyDateRangeSameDay() {
        String actual = DateViewUtil.getPrettyDateRangeDateTime(mockTime, mockTime.plusMinutes(10));
        assertEquals("Wed 24 July 1996,  08:00 - 08:10", actual);
    }

    @Test
    void getPrettyDateRangeDifferentDay() {
        String actual = DateViewUtil.getPrettyDateRangeDateTime(mockTime, mockTime.plusDays(1));
        assertEquals("24 July 1996, 08:00, Wed - 25 July 1996, 08:00, Thu", actual);
    }

    @Test
    void getPrettyTimeDifference() {
        String actual = DateViewUtil.getPrettyTimeDifference(mockTime, mockTime.plusDays(1));
        assertEquals("24:00:00", actual);
    }

    @Test
    void getPrettyDuration() {
        String actual = DateViewUtil.getPrettyDuration(mockDuration);
        assertEquals("1h 2m 3s", actual);
    }

    @Test
    void getDurationInMinutes() {
        Long actualMinutes = DateViewUtil.getDurationInMinutes(mockTime,
            mockTime.plusHours(1).plusMinutes(23).plusSeconds(2));
        assertEquals(83L, actualMinutes);
    }

    // Regression Test
    // See: https://github.com/AY1920S2-CS2103T-W16-2/main/issues/230
    @Test
    void getPrettyDuration_withSubSeconds_success() {
        assertFalse(DateViewUtil.getPrettyDuration(Duration.ofHours(2).plusNanos(123).plusMillis(123)).contains("."));
    }
}
