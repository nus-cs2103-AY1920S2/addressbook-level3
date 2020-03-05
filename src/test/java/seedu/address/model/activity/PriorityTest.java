package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void isValidPriority_validPriority_true() {
        boolean result = Priority.isValidPriorityValue("3");
        assertEquals(true, result);
    }

    @Test
    public void isValidPriority_invalidPriority_false() {
        /*
         * Test for empty string, or whitespace characters
         */
        assertEquals(false, Priority.isValidPriorityValue("2.0"));
        assertEquals(false, Priority.isValidPriorityValue("3.5"));
        assertEquals(false, Priority.isValidPriorityValue("-1"));
        assertEquals(false, Priority.isValidPriorityValue("10"));
        assertEquals(false, Priority.isValidPriorityValue("abc"));
    }

    @Test
    public void priority_validInstantiation_noException() throws IllegalArgumentException {
        Priority priority = new Priority();
        priority = new Priority("1");
        priority = new Priority("5");
    }

    @Test
    public void priority_invalidInstantiation_exceptionThrown() {
        try {
            Priority note = new Priority("6");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("java.lang.IllegalArgumentException: " + Priority.PRIORITY_RANGE_CONSTRAINTS, e.toString());
        }
    }
}
