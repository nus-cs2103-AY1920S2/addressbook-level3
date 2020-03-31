package seedu.recipe.model.plan;

import java.util.List;
import java.util.Objects;

import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * Represents a planned recipe in the planned recipes book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PlannedRecipe implements Comparable<PlannedRecipe> {

    private List<Recipe> recipes;
    private PlannedDate date;

    public PlannedRecipe(List<Recipe> recipes, PlannedDate date) {
        this.recipes = recipes;
        this.date = date;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Replaces the {@code target} Recipe with the {@code newRecipe} recipe in the planned recipe's list of recipes.
     */
    public void setRecipe(Recipe target, Recipe newRecipe) {
        int index = recipes.indexOf(target);
        recipes.set(index, newRecipe);
    }

    public PlannedDate getDate() {
        return date;
    }

    /**
     * Adds the recipes in {@code plannedRecipe} to the recipes in this Planned Recipe object and returns a new
     * Planned Recipe object.
     */
    public PlannedRecipe plannedRecipeWithAllPlanned(PlannedRecipe plannedRecipe) {
        if (recipes.contains(plannedRecipe)) {
            throw new DuplicateRecipeException();
        }
        List<Recipe> newRecipes = plannedRecipe.getRecipes();
        newRecipes.addAll(recipes);
        return new PlannedRecipe(newRecipes, date);
    }

    public boolean sameDate(PlannedRecipe other) {
        return date.equals(other.date);
    }

    /**
     * Checks whether the date of this planned recipe falls within the {@code start} date and the {@code end} date.
     */
    public boolean isWithinRange(PlannedDate start, PlannedDate end) {
        return date.isWithinRange(start, end);
    }

    @Override
    public int compareTo(PlannedRecipe other) {
        return date.compareTo(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes, date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedRecipe // instanceof handles nulls
                && date.equals(((PlannedRecipe) other).date) // state check
                && recipes.equals(((PlannedRecipe) other).recipes));
    }
}
