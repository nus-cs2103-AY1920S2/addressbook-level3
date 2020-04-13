package fithelper.model.entry;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DurationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidDuration = "";
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }

    @Test
    public void isValidDuration() {
        // null email
        assertThrows(NullPointerException.class, () -> Duration.isValidDuration(null));

        // blank email
        assertFalse(Duration.isValidDuration("")); // empty string
        assertFalse(Duration.isValidDuration(" ")); // spaces only

        // invalid values
        assertFalse(Duration.isValidDuration("-0.6")); // negative value
        assertFalse(Duration.isValidDuration("-34.0")); // negative value

        // valid values
        assertTrue(Duration.isValidDuration("5.0"));
        assertTrue(Duration.isValidDuration("0.5"));
    }
}
