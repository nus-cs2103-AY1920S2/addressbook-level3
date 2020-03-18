package nasa.model.activity;

import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void isValidPriorityValue() {
        // valid priority
        assertEquals(true, Priority.isValidPriorityValue("3"));
        assertEquals(true, Priority.isValidPriorityValue("1"));

        // invalid priority
        assertEquals(false, Priority.isValidPriorityValue("2.0"));
        assertEquals(false, Priority.isValidPriorityValue("3.5"));
        assertEquals(false, Priority.isValidPriorityValue("-1"));
        assertEquals(false, Priority.isValidPriorityValue("10"));
        assertEquals(false, Priority.isValidPriorityValue("a"));
    }

    @Test
    public void constructor_invalidNumber_throwsIllegalArgumentException() {
        // invalid number beyond range of 1 to 5 inclusive
        assertThrows(IllegalArgumentException.class, () -> new Priority("6"));
        assertThrows(IllegalArgumentException.class, () -> new Priority("0"));
        assertThrows(IllegalArgumentException.class, () -> new Priority("-3"));
        assertThrows(IllegalArgumentException.class, () -> new Priority("01"));
        assertThrows(IllegalArgumentException.class, () -> new Priority("20"));

        // invalid number: non-numeric characters
        assertThrows(IllegalArgumentException.class, () -> new Priority("d"));
        assertThrows(IllegalArgumentException.class, () -> new Priority("testing string"));
    }
}
