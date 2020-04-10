package team.easytravel.model.listmanagers.packinglistitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ItemCategoryTest {
    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemCategory(null));
    }

    @Test
    public void constructorInvalidCategoryThrowsIllegalArgumentException() {
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemCategory(invalidCategory));
    }

    @Test
    public void isValidItemCategory() {
        // null category
        assertThrows(NullPointerException.class, () -> ItemCategory.isValidItemCategory(null));

        // blank category
        assertFalse(ItemCategory.isValidItemCategory("")); // Empty string
        assertFalse(ItemCategory.isValidItemCategory(" ")); // Spaces only

        // invalid category
        assertFalse(ItemCategory.isValidItemCategory("^")); // Non-alphanumeric characters
        assertFalse(ItemCategory.isValidItemCategory("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(ItemCategory.isValidItemCategory(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // Contains 34 characters

        // valid category
        assertTrue(ItemCategory.isValidItemCategory("car")); //Normal case
        assertTrue(ItemCategory.isValidItemCategory("essentials20")); // Contains alphanumeric characters.
        assertTrue(ItemCategory.isValidItemCategory("123456")); // Contains alphanumeric characters.
        assertTrue(ItemCategory.isValidItemCategory(
                "formal dinner female")); //Contains 20 alphanumeric char and space
        assertTrue(ItemCategory.isValidItemCategory(
                "international"));

    }

    @Test
    public void testToString() {
        assertEquals("others", new ItemCategory("others").toString());
        assertEquals("essentials", new ItemCategory("ESSENTIALS").toString());
        assertEquals("swimming", new ItemCategory("sWImMiNg").toString());

        //Wrong toString
        assertNotEquals("international", new ItemCategory("clothes").toString());
    }

    @Test
    public void testEquals() {
        //Equal ItemCategory Object
        assertEquals(new ItemCategory("beach"),
                new ItemCategory("beach")); // When two categories are the same.

        //Non Equal ItemCategory Object
        assertNotEquals(new ItemCategory("camping"),
                new ItemCategory("HIKING")); // When two categories are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new ItemCategory("toiletries").hashCode(),
                new ItemCategory("toiletries").hashCode());

        // Equal Hashcode
        assertEquals(new ItemCategory("BEacH").hashCode(),
                new ItemCategory("beach").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new ItemCategory("PHOTOGraphy").hashCode(),
                new ItemCategory("others").hashCode());
    }
}
