package seedu.recipe.model.plan;

import java.util.function.Predicate;

public class DateWithinRangePredicate implements Predicate<PlannedRecipe> {

    PlannedDate start;
    PlannedDate end;

    public DateWithinRangePredicate(PlannedDate start, PlannedDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean test(PlannedRecipe recipe) {
        return recipe.isWithinRange(start, end);
    }
}
