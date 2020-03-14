package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.ALICE;
import static seedu.address.testutil.TypicalRecipes.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecipeBuilder;

public class RecipeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new RecipeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recipe.getGoals().remove(0));
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(ALICE.isSameRecipe(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameRecipe(null));

        // different time and step -> returns false
        Recipe editedAlice = new RecipeBuilder(ALICE).withTime(VALID_TIME_BOB).withStep(VALID_STEP_BOB).build();
        assertFalse(ALICE.isSameRecipe(editedAlice));

        // different name -> returns false
        editedAlice = new RecipeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameRecipe(editedAlice));

        // same name, same time, different attributes -> returns true
        editedAlice = new RecipeBuilder(ALICE).withStep(VALID_STEP_BOB).withGoals(VALID_GOAL_HUSBAND).build();
        assertTrue(ALICE.isSameRecipe(editedAlice));

        // same name, same step, different attributes -> returns true
        editedAlice = new RecipeBuilder(ALICE).withTime(VALID_TIME_BOB).withGoals(VALID_GOAL_HUSBAND).build();

        assertTrue(ALICE.isSameRecipe(editedAlice));

        // same name, same time, same step, different attributes -> returns true
        editedAlice = new RecipeBuilder(ALICE).withGoals(VALID_GOAL_HUSBAND).build();
        assertTrue(ALICE.isSameRecipe(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new RecipeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different recipe -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different time -> returns false
        editedAlice = new RecipeBuilder(ALICE).withTime(VALID_TIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different step -> returns false
        editedAlice = new RecipeBuilder(ALICE).withStep(VALID_STEP_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different goals -> returns false
        editedAlice = new RecipeBuilder(ALICE).withGoals(VALID_GOAL_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
