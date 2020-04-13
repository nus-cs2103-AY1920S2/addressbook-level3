package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RecipeDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidRecipeDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new RecipeDescription(invalidRecipeDescription));
    }

    @Test
    public void isValidRecipeDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> RecipeDescription.isValidRecipeDescription(null));

        // invalid description
        assertFalse(RecipeDescription.isValidRecipeDescription("")); // empty string
        assertFalse(RecipeDescription.isValidRecipeDescription(" ")); // spaces only

        // valid description
        assertTrue(RecipeDescription.isValidRecipeDescription("eggs scrambled with pepper")); // alphabets only
        assertTrue(RecipeDescription.isValidRecipeDescription("11223344")); // numbers only
        assertTrue(RecipeDescription.isValidRecipeDescription("1st in the world")); // alphanumeric characters
        assertTrue(RecipeDescription.isValidRecipeDescription("Succulent Pork Belly")); // with capital letters
        assertTrue(RecipeDescription.isValidRecipeDescription("A-grade")); // contains non-alphanumeric characters
        assertTrue(RecipeDescription.isValidRecipeDescription("Roasted cod with crushed potatoes, artichoke, "
                + "salted capers, red wine and lemon sauce")); // long descriptions
    }
}
