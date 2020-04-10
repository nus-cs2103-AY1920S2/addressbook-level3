package team.easytravel.model.listmanagers.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class DurationTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructorInvalidDurationThrowsIllegalArgumentException() {
        Integer invalidDuration = 0;
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }


    @Test
    public void isValidDuration() {

        // null Duration
        assertThrows(NullPointerException.class, () -> Duration.isValidDuration(null));

        // Invalid Duration
        assertFalse(Duration.isValidDuration(0)); // Zero duration
        assertFalse(Duration.isValidDuration(-10)); // Negative Duration
        assertFalse(Duration.isValidDuration(1000));

        //Valid Duration
        assertTrue(Duration.isValidDuration(23)); // Normal case
        assertTrue(Duration.isValidDuration(1)); // Only 1 digit
        assertTrue(Duration.isValidDuration(023)); //When a 0 is placed in front
        assertTrue(Duration.isValidDuration(0000000000000001)); // Trailing 0s
    }

    @Test
    public void testToString() {
        assertEquals("2", new Duration(2).toString());
    }

    @Test
    public void testEquals() {
        assertEquals(new Duration(2), new Duration(2)); // When two duration are the same.
        assertNotEquals(new Duration(2), new Duration(3)); // When two durations are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Duration(20).hashCode(), new Duration(20).hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Duration(13).hashCode(), new Duration(15).hashCode());
    }
}
