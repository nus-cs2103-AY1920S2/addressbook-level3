package seedu.recipe.model.plan;

import java.util.function.Predicate;

/**
 * Tests that a {@code PlannedRecipe}'s {@code PlannedDate} falls within the specified PlannedDate range.
 */
public class PlannedRecipeWithinDateRangePredicate implements Predicate<PlannedRecipe> {

    private PlannedDate start;
    private PlannedDate end;

    public PlannedRecipeWithinDateRangePredicate(PlannedDate start, PlannedDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean test(PlannedRecipe recipe) {
        return recipe.isWithinRange(start, end);
    }
}
