package fithelper.model.entry;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CalorieTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Calorie(null));
    }

    @Test
    public void constructor_invalidCalorie_throwsIllegalArgumentException() {
        String invalidCalorie = "";
        assertThrows(IllegalArgumentException.class, () -> new Calorie(invalidCalorie));
    }

    @Test
    public void isValidCalorie() {
        // null calorie
        assertThrows(NullPointerException.class, () -> Calorie.isValidCalorie(null));

        // invalid calories
        assertFalse(Calorie.isValidCalorie("")); // empty string
        assertFalse(Calorie.isValidCalorie(" ")); // spaces only
        assertFalse(Calorie.isValidCalorie("-0.5")); // negative calorie

        // valid calorie
        assertTrue(Calorie.isValidCalorie("0.555"));
        assertTrue(Calorie.isValidCalorie("400.0"));
    }
}
