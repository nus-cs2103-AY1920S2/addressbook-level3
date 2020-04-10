package seedu.recipe.model.plan;

import java.util.function.Predicate;

import seedu.recipe.model.Date;

/**
 * Tests that a PlannedDate PlannedDate is on the specified Date. todo
 */
public class PlannedRecipeOnDatePredicate implements Predicate<Plan> {

    private Date onDate;

    public PlannedRecipeOnDatePredicate(Date onDate) {
        this.onDate = onDate;
    }

    @Override
    public boolean test(Plan recipe) {
        return recipe.isOnDate(onDate);
    }

}
