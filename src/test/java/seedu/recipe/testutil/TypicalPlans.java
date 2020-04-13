package seedu.recipe.testutil;

import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_PAST;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;
import static seedu.recipe.testutil.TypicalRecipes.BOILED_CHICKEN;
import static seedu.recipe.testutil.TypicalRecipes.CAESAR_SALAD;
import static seedu.recipe.testutil.TypicalRecipes.GRILLED_SANDWICH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Plan} objects to be used in tests.
 */
public class TypicalPlans {

    public static final Plan CAESAR_FUTURE_PLAN = new Plan(CAESAR_SALAD, DATE_IN_FUTURE);

    public static final Plan SANDWICH_PAST_PLAN = new Plan(GRILLED_SANDWICH, DATE_IN_PAST);

    public static final Plan CHICKEN_TODAY_PLAN = new Plan(BOILED_CHICKEN, DATE_TODAY);

    private TypicalPlans() {} // prevents instantiation

    /**
     * Returns an {@code PlannedBook} with all the typical plans.
     */
    public static PlannedBook getTypicalPlannedBook() {
        PlannedBook plannedBook = new PlannedBook();
        for (Plan plan : getTypicalPlans()) {
            Recipe forRecipe = plan.getRecipe();
            plannedBook.addPlan(forRecipe, plan);
        }
        return plannedBook;
    }

    public static List<Plan> getTypicalPlans() {
        return new ArrayList<>(Arrays.asList(CAESAR_FUTURE_PLAN, SANDWICH_PAST_PLAN, CHICKEN_TODAY_PLAN));
    }
}
