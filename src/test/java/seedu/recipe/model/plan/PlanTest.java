package seedu.recipe.model.plan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;
import static seedu.recipe.testutil.TypicalRecipes.CAESAR_SALAD;
import static seedu.recipe.testutil.TypicalRecipes.GRILLED_SANDWICH;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;

public class PlanTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // both null
        assertThrows(NullPointerException.class, () -> new Plan(null, null));

        // null date
        Recipe validRecipe = new RecipeBuilder().build();
        assertThrows(NullPointerException.class, () -> new Plan(validRecipe, null));

        // null recipe
        assertThrows(NullPointerException.class, () -> new Plan(null, DATE_TODAY));
    }

    @Test
    public void equals() {
        Plan caesarTodayPlan = new Plan(CAESAR_SALAD, DATE_TODAY);

        // same plan
        assertTrue(caesarTodayPlan.equals(caesarTodayPlan));

        // same values
        Plan caesarTodayPlanCopy = new Plan(CAESAR_SALAD, DATE_TODAY);
        assertTrue(caesarTodayPlan.equals(caesarTodayPlanCopy));

        // different recipe
        Plan sandwichTodayPlan = new Plan(GRILLED_SANDWICH, DATE_TODAY);
        assertFalse(caesarTodayPlan.equals(sandwichTodayPlan));

        // different date
        Plan caesarFuturePlan = new Plan(CAESAR_SALAD, DATE_IN_FUTURE);
        assertFalse(caesarTodayPlan.equals(caesarFuturePlan));

        // different recipe and date
        Plan sandwichFuturePlan = new Plan(GRILLED_SANDWICH, DATE_IN_FUTURE);
        assertFalse(caesarTodayPlan.equals(sandwichFuturePlan));
    }
}
