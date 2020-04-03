package seedu.recipe.model.plan;

import java.util.function.Predicate;

import seedu.recipe.model.Date;

/**
 * Tests that a PlannedDate PlannedDate is on the specified Date.
 */
public class PlannedRecipeOnDatePredicate implements Predicate<PlannedDate> {

    private Date onDate;

    public PlannedRecipeOnDatePredicate(Date onDate) {
        this.onDate = onDate;
    }

    @Override
    public boolean test(PlannedDate recipe) {
        return recipe.isOnDate(onDate);
    }

}
