package seedu.recipe.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;

import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalRecipes.CAESAR_SALAD;
import static seedu.recipe.testutil.TypicalRecipes.FISH;

import org.junit.jupiter.api.Test;

import seedu.recipe.testutil.RecipeBuilder;

public class RecipeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new RecipeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recipe.getGoals().remove(0));
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(CAESAR_SALAD.isSameRecipe(CAESAR_SALAD));

        // null -> returns false
        assertFalse(CAESAR_SALAD.isSameRecipe(null));

        // different time and email -> returns false
        Recipe editedCaesar = new RecipeBuilder(CAESAR_SALAD)
                .withTime(VALID_TIME_FISH).withSteps(VALID_STEP_FISH).build();
        assertFalse(CAESAR_SALAD.isSameRecipe(editedCaesar));

        // different name -> returns false
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withName(VALID_NAME_FISH).build();
        assertFalse(CAESAR_SALAD.isSameRecipe(editedCaesar));

        // same name, same time, different attributes -> returns true
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withSteps(VALID_STEP_FISH)
                .withGoals(VALID_GOAL_PROTEIN).build();
        assertTrue(CAESAR_SALAD.isSameRecipe(editedCaesar));

        // same name, same email, different attributes -> returns true
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withTime(VALID_TIME_FISH).withGoals(VALID_GOAL_PROTEIN).build();
        assertTrue(CAESAR_SALAD.isSameRecipe(editedCaesar));

        // same name, same time, same email, different attributes -> returns true
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withGoals(VALID_GOAL_PROTEIN).build();
        assertTrue(CAESAR_SALAD.isSameRecipe(editedCaesar));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new RecipeBuilder(CAESAR_SALAD).build();
        assertTrue(CAESAR_SALAD.equals(aliceCopy));

        // same object -> returns true
        assertTrue(CAESAR_SALAD.equals(CAESAR_SALAD));

        // null -> returns false
        assertFalse(CAESAR_SALAD.equals(null));

        // different type -> returns false
        assertFalse(CAESAR_SALAD.equals(5));

        // different recipe -> returns false
        assertFalse(CAESAR_SALAD.equals(FISH));

        // different name -> returns false
        Recipe editedCaesar = new RecipeBuilder(CAESAR_SALAD).withName(VALID_NAME_FISH).build();
        assertFalse(CAESAR_SALAD.equals(editedCaesar));


        // different email -> returns false
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withSteps(VALID_STEP_FISH).build();
        assertFalse(CAESAR_SALAD.equals(editedCaesar));

        // different goals -> returns false
        editedCaesar = new RecipeBuilder(CAESAR_SALAD).withGoals(VALID_GOAL_PROTEIN).build();
        assertFalse(CAESAR_SALAD.equals(editedCaesar));
    }
}
