package seedu.address.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidDateOnly));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidTimeOnly));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidDate));
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(invalidTime));
    }

    @Test
    public void isValidTimeStamp() {
        String invalidDateOnly = "2019-20-02";
        String invalidTimeOnly = "0213";
        String invalidDate = "2019-02-29 2000";
        String invalidTime = "2020-02-20 2512";

        // null timeStamp
        assertThrows(NullPointerException.class, () -> TimeStamp.isValidTimeStamp(null));

        // invalid timeStamp
        assertFalse(TimeStamp.isValidTimeStamp("")); // empty string
        assertFalse(TimeStamp.isValidTimeStamp(" ")); // spaces only
        assertFalse(TimeStamp.isValidTimeStamp(invalidDateOnly)); // date only
        assertFalse(TimeStamp.isValidTimeStamp(invalidTimeOnly)); // time only
        assertFalse(TimeStamp.isValidTimeStamp(invalidDate)); // invalid date
        assertFalse(TimeStamp.isValidTimeStamp(invalidTime)); // invalid time


        // valid timeStamp
        assertTrue(TimeStamp.isValidTimeStamp("2020-05-20 1500"));
    }
}
