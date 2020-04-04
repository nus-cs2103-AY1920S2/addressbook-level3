package seedu.zerotoone.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    void isValidDateTime() {
        assertTrue(DateTime.isValidDateTime("2015-08-12 12:24"));

        assertFalse(DateTime.isValidDateTime("2020-02-02 2200"));
        assertFalse(DateTime.isValidDateTime("2020-02-31 12:00"));
    }

    @Test
    void isDateEqualOrLaterThan() {
        DateTime dtOne;
        DateTime dtTwo;

        // same date, same time -> true
        dtOne = new DateTime("2020-04-04 00:00");
        dtTwo = new DateTime("2020-04-04 00:00");
        assertTrue(dtOne.isDateEqualOrLaterThan(dtTwo));

        // same date, different time -> both true
        dtOne = new DateTime("2020-04-04 00:00");
        dtTwo = new DateTime("2020-04-04 03:00");
        assertTrue(dtOne.isDateEqualOrLaterThan(dtTwo));
        assertTrue(dtTwo.isDateEqualOrLaterThan(dtOne));

        // different date, same time -> depends
        dtOne = new DateTime("2020-04-03 00:00");
        dtTwo = new DateTime("2020-04-04 00:00");
        assertFalse(dtOne.isDateEqualOrLaterThan(dtTwo));
        assertTrue(dtTwo.isDateEqualOrLaterThan(dtOne));
    }
}
