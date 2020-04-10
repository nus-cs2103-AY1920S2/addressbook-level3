package team.easytravel.model.listmanagers.fixedexpense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FixedExpenseCategoryTest {


    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FixedExpenseCategory(null));
    }

    @Test
    public void constructorInvalidCategoryThrowsIllegalArgumentException() {
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new FixedExpenseCategory(invalidCategory));
    }

    @Test
    public void isValidCategory() {
        // null category
        assertThrows(NullPointerException.class, () -> FixedExpenseCategory.isValidCategory(null));

        // blank category
        assertFalse(FixedExpenseCategory.isValidCategory("")); // Empty string
        assertFalse(FixedExpenseCategory.isValidCategory(" ")); // Spaces only

        // invalid category
        assertFalse(FixedExpenseCategory.isValidCategory("^")); // Non-alphanumeric characters
        assertFalse(FixedExpenseCategory.isValidCategory("Dog*")); // Contains non-alphanumeric characters.

        // valid category
        assertTrue(FixedExpenseCategory.isValidCategory("transport")); //Normal case
        assertTrue(FixedExpenseCategory.isValidCategory("accommodations")); // Contains alphanumeric characters.
        assertTrue(FixedExpenseCategory.isValidCategory(
                "activities")); //Contains 30 alphanumeric char.
        assertTrue(FixedExpenseCategory.isValidCategory(
                "others")); // Contains 34 characters.

    }

    @Test
    public void testToString() {
        assertEquals("others", new FixedExpenseCategory("others").toString());
        assertEquals("transport", new FixedExpenseCategory("TRANSPORT").toString());
        assertEquals("accommodations", new FixedExpenseCategory("AccOmmODATions").toString());

        //Wrong toString
        assertNotEquals("transport", new FixedExpenseCategory("accommodations").toString());
    }

    @Test
    public void testEquals() {
        //Equal FixedExpenseCategory Object
        assertEquals(new FixedExpenseCategory("others"),
                new FixedExpenseCategory("others")); // When two categories are the same.

        //Non Equal FixedExpenseCategory Object
        assertNotEquals(new FixedExpenseCategory("transport"),
                new FixedExpenseCategory("ACCOMMODATIONS")); // When two categories are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new FixedExpenseCategory("transport").hashCode(),
                new FixedExpenseCategory("transport").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new FixedExpenseCategory("ACCOMMOdAtions").hashCode(),
                new FixedExpenseCategory("others").hashCode());
    }
}
