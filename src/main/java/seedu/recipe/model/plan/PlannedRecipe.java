package seedu.recipe.model.plan;

import java.util.Objects;

import seedu.recipe.model.recipe.Recipe;

public class PlannedRecipe {

    private Recipe recipe;
    private PlannedDate date;

    public PlannedRecipe(Recipe recipe, PlannedDate date) {
        this.recipe = recipe;
        this.date = date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public PlannedDate getDate() {
        return date;
    }

    public boolean isWithinRange(PlannedDate start, PlannedDate end) {
        return date.isWithinRange(start, end);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(recipe, date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedRecipe // instanceof handles nulls
                && date.equals(((PlannedRecipe) other).date)
                && recipe.equals(((PlannedRecipe) other).recipe));
    }


}
