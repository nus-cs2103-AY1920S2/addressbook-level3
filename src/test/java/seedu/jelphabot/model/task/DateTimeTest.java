package seedu.jelphabot.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime((String) null));
        assertThrows(NullPointerException.class, () -> new DateTime((LocalDateTime) null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime_invalidDateTime_returnsFalse() {
        // null datetime
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        // invalid DateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("Jan-1-2020")); // date with no time
        assertFalse(DateTime.isValidDateTime("11 11")); // time with no date

        // invalid variations of MMM-d-yyyy HH mm / d-MMM-yyyy
        assertFalse(DateTime.isValidDateTime("2020-1-May 22 00")); // wrong format: yyyy-d-MM HH mm
        assertFalse(DateTime.isValidDateTime("05-1-2020 22 00")); // wrong format: MM-d-yyyy HH mm

        // invalid variations of MMM/d/yyyy HH mm / d/M/y
        assertFalse(DateTime.isValidDateTime("May1/2020 22 00")); // Missing / between month and day
        assertFalse(DateTime.isValidDateTime("1May/2020 22 00")); // Missing / between month and day
        assertFalse(DateTime.isValidDateTime("May/12020 22 00")); // Missing / between month and year
        assertFalse(DateTime.isValidDateTime("1/May2020 22 00")); // Missing / between month and year
        assertFalse(DateTime.isValidDateTime("May12020 22 00")); // Missing / between month, day and year
        assertFalse(DateTime.isValidDateTime("1May2020 22 00")); // Missing / between month, day and year

        //invalid variations of d MMM yyyy HH mm
        assertFalse(DateTime.isValidDateTime("May 2 2020 22 00")); // wrong format: MMM d yyyy HH mm
        assertFalse(DateTime.isValidDateTime("2020 2 May 22 00")); // wrong format: yyy d MMM
        assertFalse(DateTime.isValidDateTime("2 05 2020 22 00")); // wrong format: d MM yyyy
    }

    @Test
    public void construct() {
        DateTimeFormatter formatter = DateTime.STANDARD_FORMATTER;
        String stringRep = formatter.format(LocalDateTime.now());
        LocalDateTime fromFormat = LocalDateTime.parse(stringRep, formatter);
    }

    @Test
    public void isValidDateTime_validDateTime_returnsTrue() {
        // valid DateTime formats
        assertTrue(DateTime.isValidDateTime("May-1-2020 11 11")); // MMM-d-yyyy HH mm
        assertTrue(DateTime.isValidDateTime("1-May-2020 11 11")); // d-MMM-yyyy HH mm
        assertTrue(DateTime.isValidDateTime("May/1/2020 22 00")); // MMM/d/yyyy HH mm
        assertTrue(DateTime.isValidDateTime("1/1/2020 12 00")); // d/M/y HH mm
        assertTrue(DateTime.isValidDateTime("2 May 2020 20 20")); // d MMM yyyy HH mm
    }

    @Test
    public void constructor_validDateTime_success() {
        assertEquals(new DateTime("May-1-2020 11 11"), new DateTime("May-1-2020 11 11"));
        assertEquals(new DateTime("1-May-2020 00 00"), new DateTime("May-1-2020 00 00"));
        assertEquals(new DateTime("May/1/2020 22 00"), new DateTime("May-1-2020 22 00"));
        assertEquals(new DateTime("1/5/2020 12 00"), new DateTime("May-1-2020 12 00"));
        assertEquals(new DateTime("1 May 2020 23 59"), new DateTime("May-1-2020 23 59"));
    }

    @Test
    public void getValue_returnsCorrectDisplayString() {
        assertEquals(new DateTime("May-1-2020 11 11").getDisplayValue(), "1-May-2020 11 11");
        assertEquals(new DateTime("1-May-2020 00 00").getDisplayValue(), "1-May-2020 00 00");
        assertEquals(new DateTime("May/1/2020 22 00").getDisplayValue(), "1-May-2020 22 00");
        assertEquals(new DateTime("1/5/2020 12 00").getDisplayValue(), "1-May-2020 12 00");
        assertEquals(new DateTime("1 May 2020 23 59").getDisplayValue(), "1-May-2020 23 59");
    }
}
