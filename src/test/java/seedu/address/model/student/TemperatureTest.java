package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TemperatureTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Temperature(null));
    }

    @Test
    public void constructor_invalidTemperature_throwsIllegalArgumentException() {
        String invalidTemperature = "";
        assertThrows(IllegalArgumentException.class, () -> new Temperature(invalidTemperature));
    }

    @Test
    public void isValidTemperature() {
        // null temperatures
        assertThrows(NullPointerException.class, () -> Temperature.isValidTemperatureFirst(null));

        // invalid temperatures
        assertFalse(Temperature.isValidTemperatureFirst("")); // empty string
        assertFalse(Temperature.isValidTemperatureFirst(" ")); // spaces only
        assertFalse(Temperature.isValidTemperatureFirst("91")); // less than 3 numbers
        assertFalse(Temperature.isValidTemperatureFirst("temperature")); // non-numeric
        assertFalse(Temperature.isValidTemperatureFirst("2f.5")); // alphabets within digits
        assertFalse(Temperature.isValidTemperatureFirst("23/5")); // invalid "/" symbol
        assertFalse(Temperature.isValidTemperatureFirst("1 5.3")); // spaces within digits
        assertFalse(Temperature.isValidTemperatureSecond("24.9")); //temp too low
        assertFalse(Temperature.isValidTemperatureSecond("41.1")); //temp too high

        // valid temperatures
        assertTrue(Temperature.isValidTemperatureFirst("Insert temperature here!")); // Insert comment allowed
        assertTrue(Temperature.isValidTemperatureFirst("36.5")); // 2 digits, followed by a decimal place
        assertTrue(Temperature.isValidTemperatureSecond("Insert temperature here!")); // Insert comment allowed
        assertTrue(Temperature.isValidTemperatureSecond("36.5")); // temp within valid temp range
    }
}
