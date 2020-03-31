package seedu.recipe.model.plan;

import java.util.Objects;

import seedu.recipe.model.Date;
import seedu.recipe.model.recipe.Recipe;

/**
 * Represents a planned recipe in the planned recipes book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PlannedRecipe {

    private Recipe recipe;
    private Date date;

    public PlannedRecipe(Recipe recipe, Date date) {
        this.recipe = recipe;
        this.date = date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Checks whether the date of this planned recipe falls within the {@code start} date and the {@code end} date.
     */
    public boolean isWithinRange(Date start, Date end) {
        return date.isWithinRange(start, end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedRecipe // instanceof handles nulls
                && date.equals(((PlannedRecipe) other).date) // state check
                && recipe.equals(((PlannedRecipe) other).recipe));
    }
}
