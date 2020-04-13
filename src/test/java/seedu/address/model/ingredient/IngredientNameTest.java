package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IngredientNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IngredientName(null));
    }

    @Test
    public void constructor_invalidIngredientName_throwsIllegalArgumentException() {
        String invalidIngredientName = "";
        assertThrows(IllegalArgumentException.class, () -> new IngredientName(invalidIngredientName));
    }

    @Test
    public void isValidIngredientName() {
        // null ingredient name
        assertThrows(NullPointerException.class, () -> IngredientName.isValidIngredientName(null));

        // invalid ingredient name
        assertFalse(IngredientName.isValidIngredientName("")); // empty string
        assertFalse(IngredientName.isValidIngredientName(" ")); // spaces only
        assertFalse(IngredientName.isValidIngredientName("r!ce")); // contains invalid symbol

        // valid ingredient name
        assertTrue(IngredientName.isValidIngredientName("eggs")); // lower case alphabets only
        assertTrue(IngredientName.isValidIngredientName("EGGS")); // lower case alphabets only
        assertTrue(IngredientName.isValidIngredientName("12345")); // numbers only
        assertTrue(IngredientName.isValidIngredientName("100 calorie yogurt")); // alphanumeric characters
        assertTrue(IngredientName.isValidIngredientName("Full Cream Milk")); // with capital letters
        assertTrue(IngredientName.isValidIngredientName("Full Cream Milk ")); // with trailing whitespace
        assertTrue(IngredientName.isValidIngredientName("Hershey's Kisses")); // with apostrophe
        assertTrue(IngredientName.isValidIngredientName("Self-raising flour")); // with dash
        assertTrue(IngredientName.isValidIngredientName("70% dark chocolate")); // with numbers and percent sign
        assertTrue(IngredientName.isValidIngredientName("Organic whole milk Greek yogurt")); // long ingredient names
    }

    @Test
    public void toString_validIngredient_returnsStringRepresentation() {
        assertEquals("eggs", new IngredientName("eggs").toString()); // lower case alphabets only
        assertEquals("EGGS", new IngredientName("EGGS").toString()); // upper case alphabets only
        assertEquals("12345", new IngredientName("12345").toString()); // numbers only
        assertEquals("100 calorie yogurt",
                new IngredientName("100 calorie yogurt").toString()); // alphanumeric characters
        assertEquals("Full Cream Milk", new IngredientName("Full Cream Milk").toString()); // with capital letter
        assertEquals("Full Cream Milk", new IngredientName("Full Cream Milk ").toString()); // with trailing whitespace
        assertEquals("Hershey's Kisses", new IngredientName("Hershey's Kisses").toString()); // with apostrophe
        assertEquals("Self-raising flour", new IngredientName("Self-raising flour").toString()); // with dash
        assertEquals("70% dark chocolate",
                new IngredientName("70% dark chocolate").toString()); // with numbers and percent sign
        assertEquals("Organic whole milk Greek yogurt",
                new IngredientName("Organic whole milk Greek yogurt").toString()); // long ingredient names
    }

    @Test
    public void equals() {
        IngredientName testIngredientName = new IngredientName("Eggs");

        // same name -> returns true
        assertTrue(testIngredientName.equals(new IngredientName("Eggs")));

        // same object -> returns true
        assertTrue(testIngredientName.equals(testIngredientName));

        // same name with different case -> returns true
        assertTrue(testIngredientName.equals(new IngredientName("eggs")));

        // same name with trailing whitespace -> returns true
        assertTrue(testIngredientName.equals(new IngredientName("Eggs ")));

        // null -> returns false
        assertFalse(testIngredientName.equals(null));

        // different ingredient name -> returns false
        assertFalse(testIngredientName.equals(new IngredientName("Milk")));
    }
}
