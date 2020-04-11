package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PriorityTest {

    @Test
    void isValidPriority() {
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("5"));
        assertFalse(Priority.isValidPriority("6"));
        assertFalse(Priority.isValidPriority("0"));
        assertFalse(Priority.isValidPriority("dwawsdas"));
        assertFalse(Priority.isValidPriority("e32xr"));
        assertFalse(Priority.isValidPriority("low"));
    }

    @Test
    void getPriority() {
        assertEquals(Priority.getPriority("1"), Priority.VERYLOW);
        assertEquals(Priority.getPriority("2"), Priority.LOW);
        assertEquals(Priority.getPriority("3"), Priority.MEDIAN);
        assertEquals(Priority.getPriority("4"), Priority.HIGH);
        assertEquals(Priority.getPriority("5"), Priority.VERYHIGH);
    }

    @Test
    void getLevelOfSignificance() {
        Priority priority = Priority.VERYHIGH;
        assertEquals(priority.getLevelOfSignificance(), 5);
    }

    @Test
    void testToString() {
        Priority priority = Priority.HIGH;
        assertEquals(priority.toString(), "High");
    }
}
