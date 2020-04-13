package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RecipeNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidRecipeName = "";
        assertThrows(IllegalArgumentException.class, () -> new RecipeName(invalidRecipeName));
    }

    @Test
    public void isValidRecipeName() {
        // null name
        assertThrows(NullPointerException.class, () -> RecipeName.isValidRecipeName(null));

        // invalid name
        assertFalse(RecipeName.isValidRecipeName("")); // empty string
        assertFalse(RecipeName.isValidRecipeName(" ")); // spaces only

        // valid name
        assertTrue(RecipeName.isValidRecipeName("bacon carbonara")); // alphabets only
        assertTrue(RecipeName.isValidRecipeName("0123210")); // numbers only
        assertTrue(RecipeName.isValidRecipeName("number 1 cake")); // alphanumeric characters
        assertTrue(RecipeName.isValidRecipeName("Beef Wellington")); // with capital letters
        assertTrue(RecipeName.isValidRecipeName("Shepherd's Pie")); // contains non-alphanumeric characters
        assertTrue(RecipeName.isValidRecipeName("Slow-roasted Cumbrian saddleback pork belly")); // long names
    }
}
