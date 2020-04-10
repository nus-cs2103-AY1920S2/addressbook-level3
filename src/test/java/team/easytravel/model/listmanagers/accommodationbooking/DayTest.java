package team.easytravel.model.listmanagers.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

class DayTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructorInvalidDayThrowsIllegalArgumentException() {
        Integer invalidDay = -100;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {

        // null Day
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // Invalid Day
        assertFalse(Day.isValidDay(0)); // zero day
        assertFalse(Day.isValidDay(-100)); // negative day

        //TODO After Wee finds a way to check for overlap
        //assertFalse(Day.isAmount.isValidAmount("1000.000"));

        //Valid Day
        assertTrue(Day.isValidDay(10)); // With 2 digits.
        assertTrue(Day.isValidDay(1)); // normal test
    }


    @Test
    public void testToString() {
        assertEquals("10", new Day(10).toString());
    }

    @Test
    public void testEquals() {
        assertEquals(new Day(10), new Day(10)); // WHen 2 days are the same
        assertNotEquals(new Day(15), new Day(5)); // WHen 2 days are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Day(1).hashCode(), new Day(1).hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Day(1).hashCode(), new Day(2).hashCode());


    }
}
