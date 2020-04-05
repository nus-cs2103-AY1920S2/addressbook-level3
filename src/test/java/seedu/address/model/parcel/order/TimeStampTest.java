package seedu.address.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.parcel.parcelattributes.TimeStamp;

class TimeStampTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeStamp(null));
    }

    @Test
    public void constructor_invalidTimeStamp_throwsIllegalArgumentException() {
        String invalidDateOnly = "2019-20-02";
        String invalidTimeOnly = "0213";
        String invalidDate = "2019-02-29 2000";
        String invalidTime = "2020-02-20 2512";
        String timestampBeforeNow = "2020-02-01 1200";
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidDateOnly));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidTimeOnly));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidDate));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidTime));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(timestampBeforeNow));
    }

    @Test
    public void checkTimestamp_allInvalidTimestamp_giveCorrectOutput() {
        String invalidDateOnly = "2019-20-02";
        String invalidTimeOnly = "0213";
        String invalidDate = "2021-02-29 2000";
        String invalidTime = "2021-02-20 2512";
        String timeBeforeCurrent = "2020-04-03 2000";

        // null timeStamp
        assertThrows(NullPointerException.class, () -> TimeStamp.checkTimestamp(null));

        // invalid timeStamp
        assertEquals(TimeStamp.checkTimestamp(""), TimeStamp.PARSE_ERROR); // empty string
        assertEquals(TimeStamp.checkTimestamp(" "), TimeStamp.PARSE_ERROR); // spaces only
        assertEquals(TimeStamp.checkTimestamp(invalidDateOnly), TimeStamp.PARSE_ERROR); // date only
        assertEquals(TimeStamp.checkTimestamp(invalidTimeOnly), TimeStamp.PARSE_ERROR); // time only
        assertEquals(TimeStamp.checkTimestamp(invalidDate), TimeStamp.PARSE_ERROR); // invalid date
        assertEquals(TimeStamp.checkTimestamp(invalidTime), TimeStamp.PARSE_ERROR); // invalid time
        assertEquals(TimeStamp.checkTimestamp(timeBeforeCurrent), TimeStamp.TIMESTAMP_BEFORE_NOW_ERROR);

    }

    @Test
    public void checkTimestamp_validTimestamp_giveCorrectOutput() {
        String validInput = "2022-05-20 1500";

        // valid timeStamp
        assertEquals(TimeStamp.checkTimestamp(validInput), TimeStamp.VALID_TIMESTAMP);
    }
}
