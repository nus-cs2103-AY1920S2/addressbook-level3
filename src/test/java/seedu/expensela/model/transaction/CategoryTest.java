package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class CategoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    void isValidCategory() {

        // invalid category
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("^")); // only non-alphanumeric characters
        assertFalse(Category.isValidCategory("FOOD*")); // contains non-alphanumeric characters
        // assertFalse(Category.isValidCategory("RENTAL")); // category not found in CategoryEnum.java

        // valid category - only categories in CategoryEnum.java
        assertTrue(Category.isValidCategory("FOOD"));
        assertTrue(Category.isValidCategory("SHOPPING"));
        assertTrue(Category.isValidCategory("TRANSPORT"));
        assertTrue(Category.isValidCategory("GROCERIES"));
        assertTrue(Category.isValidCategory("HEALTH"));
        assertTrue(Category.isValidCategory("RECREATION"));
        assertTrue(Category.isValidCategory("MISC"));
        assertTrue(Category.isValidCategory("UTILITIES"));
        assertTrue(Category.isValidCategory("INCOME"));
    }
}
