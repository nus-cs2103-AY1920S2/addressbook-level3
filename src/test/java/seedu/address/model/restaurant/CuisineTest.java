package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CuisineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cuisine(null));
    }

    @Test
    public void isValidCuisine() {
        // null cuisine
        assertThrows(NullPointerException.class, () -> Cuisine.isValidCuisine(null));

        // invalid cuisine

        assertFalse(Cuisine.isValidCuisine("^")); // only non-alphanumeric characters
        assertFalse(Cuisine.isValidCuisine("dasd*")); // contains non-alphanumeric characters
        assertFalse(Cuisine.isValidCuisine(" ")); // spaces only

        // valid cuisine
        assertTrue(Cuisine.isValidCuisine("")); // empty string
        assertTrue(Cuisine.isValidCuisine("afsa sdads")); // alphabets only
        assertTrue(Cuisine.isValidCuisine("12345")); // numbers only
        assertTrue(Cuisine.isValidCuisine("pfas3 33as")); // alphanumeric characters
        assertTrue(Cuisine.isValidCuisine("s sZSW sZ")); // with capital letters
        assertTrue(Cuisine.isValidCuisine("sdaZ fs C 3 ")); // long cuisines
    }
}
