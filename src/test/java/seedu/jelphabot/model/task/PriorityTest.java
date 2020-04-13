//@@author eedenong
package seedu.jelphabot.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


class PriorityTest {

    @Test
    void isValidPriority() {
        // null priority
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("^")); // non-alphanumeric characters
        assertFalse(Priority.isValidPriority("high*")); // contains non-alphanumeric characters

        // valid priority
        assertTrue(Priority.isValidPriority("0")); // number between -1 and 1 inclusive
        assertTrue(Priority.isValidPriority("-1")); // number between -1 and 1 inclusive
        assertTrue(Priority.isValidPriority("HIGH")); // priority in string representation
        assertTrue(Priority.isValidPriority("LOW")); // priority in string representation
    }
}
