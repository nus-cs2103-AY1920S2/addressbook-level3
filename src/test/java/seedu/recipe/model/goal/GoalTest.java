package seedu.recipe.model.goal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.recipe.ingredient.MainIngredientType;

public class GoalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Goal((String) null));
    }

    @Test
    public void constructor_invalidGoalName_throwsIllegalArgumentException() {
        String invalidGoalName = "";
        assertThrows(IllegalArgumentException.class, () -> new Goal(invalidGoalName));
    }

    @Test
    public void isValidGoalName_throwsNullPointerException() {
        // null goal name
        assertThrows(NullPointerException.class, () -> Goal.isValidGoalName(null));
    }

    @Test
    public void isValidGoalName() {
        // null name
        assertThrows(NullPointerException.class, () -> Goal.isValidGoalName(null));

        // invalid name
        assertFalse(Goal.isValidGoalName("")); // empty string
        assertFalse(Goal.isValidGoalName(" ")); // spaces only
        assertFalse(Goal.isValidGoalName("^")); // only non-alphanumeric chars except permitted special characters.
        assertFalse(Goal.isValidGoalName("peter*")); // contains non-alphanumeric characters
        assertFalse(Goal.isValidGoalName("herbivore")); // wrong capitalization
        assertFalse(Goal.isValidGoalName("weight loss")); // not one of the given goals
        assertFalse(Goal.isValidGoalName(String.valueOf(MainIngredientType.FRUIT))); // wrong type

        // valid name
        assertTrue(Goal.isValidGoalName("Herbivore")); // valid goal for veg
        assertTrue(Goal.isValidGoalName("Fruity Fiesta")); // valid goal for fruits
        assertTrue(Goal.isValidGoalName("Bulk like the Hulk")); // valid goal for protein
        assertTrue(Goal.isValidGoalName("Wholesome Wholemeal")); // valid goal for grains
    }
}
