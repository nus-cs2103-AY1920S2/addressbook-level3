package seedu.recipe.model.plan;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.recipe.model.Date;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * Represents a planned recipe in the planned recipes book.
 * Stores the list of all recipes planned on a date.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PlannedRecipe implements Comparable<PlannedRecipe> {

    private List<Recipe> recipes;
    private Date date;

    public PlannedRecipe(List<Recipe> recipes, Date date) {
        this.recipes = recipes;
        this.date = date;
    }

    /**
     * Replaces the entire planned recipes list with {@code recipes}.
     */
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

    /**
     * Adds the recipes in {@code plannedRecipe} to the recipes in this Planned Recipe object and returns a new
     * Planned Recipe object.
     */
    public PlannedRecipe allPlannedRecipesOnOneDay(PlannedRecipe plannedRecipe) {
        if (recipes.contains(plannedRecipe)) {
            throw new DuplicateRecipeException();
        }
        List<Recipe> newRecipes = plannedRecipe.getRecipes();
        newRecipes.addAll(recipes);
        return new PlannedRecipe(newRecipes, date);
    }

    /**
     * Returns true if {@code plannedRecipe} is planned on the same day and all its recipes
     * can be found in the current PlannedRecipe's list, or if duplicate recipes are found in {@code plannedRecipe}.
     */
    public boolean hasSameRecipeInPlan(PlannedRecipe plannedRecipe) {
        if (plannedRecipe.recipesAreUnique()) {
            return date.equals(plannedRecipe.date) && recipes.containsAll(plannedRecipe.getRecipes());
        }
        return true;
    }


    /**
     * Returns true if it is planned on {@code onDate}.
     */
    public boolean isOnDate(Date onDate) {
        return date.equals(onDate);
    }

    /**
     * Returns true if the recipes in the internal recipe list consist of unique recipes.
     */
    public boolean recipesAreUnique() {
        Set<Recipe> uniqueRecipeSet = new HashSet<>();
        for (Recipe recipe: recipes) {
            if (!uniqueRecipeSet.add(recipe)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the date of this planned recipe falls within the {@code start} date and the {@code end} date.
     * The start and end date is non-inclusive.
     */
    public boolean isWithinRange(Date start, Date end) {
        return date.isWithinRange(start, end);
    }

    public Date getDate() {
        return date;
    }

    public List<Recipe> getRecipes() {
        return recipes;
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
