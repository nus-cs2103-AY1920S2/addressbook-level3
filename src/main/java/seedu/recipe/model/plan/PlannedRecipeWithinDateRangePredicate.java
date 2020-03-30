package seedu.recipe.model.plan;

import java.util.function.Predicate;

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
