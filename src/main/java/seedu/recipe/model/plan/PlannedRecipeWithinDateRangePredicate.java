package seedu.recipe.model.plan;

import java.util.function.Predicate;

import seedu.recipe.model.Date;

/**
<<<<<<< HEAD
 * Tests that a {@code PlannedRecipe}'s {@code PlannedDate} falls within the specified PlannedDate range.
 * The start and end dates are non-inclusive.
=======
 * Tests that a {@code PlannedRecipe}'s {@code Date} falls within the specified Date range.
>>>>>>> master
 */
public class PlannedRecipeWithinDateRangePredicate implements Predicate<PlannedRecipe> {

    private Date start;
    private Date end;

    public PlannedRecipeWithinDateRangePredicate(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean test(PlannedRecipe recipe) {
        return recipe.isWithinRange(start, end);
    }
}
