package seedu.eylah.diettracker.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CaloriesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Calories((Long) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        long invalidCalories = -10;
        assertThrows(IllegalArgumentException.class, () -> new Calories((Long) invalidCalories));
    }

    @Test
    public void isValidCalories() {
        // null name
        assertThrows(NullPointerException.class, () -> Calories.isValidCalories((Long) null));

        // invalid calories
        assertFalse(Calories.isValidCalories(-10)); // negative value

        // valid name
        assertTrue(Calories.isValidCalories(0)); // zero allowed
        assertTrue(Calories.isValidCalories(12345)); // numbers only
    }
}
