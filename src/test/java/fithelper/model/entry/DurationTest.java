package fithelper.model.entry;

import static fithelper.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DurationTest {

    @Test
    public void constructornullthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Duration(null));
    }

    @Test
    public void constructorinvalidEmailthrowsIllegalArgumentException() {
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
