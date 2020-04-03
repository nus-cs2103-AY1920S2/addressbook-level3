package seedu.recipe.model.plan;

import java.util.ArrayList;
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
public class PlannedDate implements Comparable<PlannedDate> {

    private List<Recipe> recipes;
    private Date date;

    public PlannedDate(List<Recipe> recipes, Date date) {
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
     * Replaces the {@code target} Recipe with the {@code newRecipe} recipe in the planned recipe's list of recipes
     * and returns a new PlannedDate.
     */
    public PlannedDate setRecipe(Recipe target, Recipe newRecipe) {
        List<Recipe> newRecipes = new ArrayList<>(recipes);
        int index = newRecipes.indexOf(target);
        newRecipes.set(index, newRecipe);
        return new PlannedDate(newRecipes, date);
    }

    /**
     * Concatenates all recipes planned on the same day and returns a new PlannedDate.
     */
    public PlannedDate addRecipes(PlannedDate plannedDate) {
        if (recipes.contains(plannedDate)) {
            throw new DuplicateRecipeException();
        }
        List<Recipe> newRecipes = new ArrayList<>(recipes);
        newRecipes.addAll(plannedDate.getRecipes());
        return new PlannedDate(newRecipes, date);
    }

    /**
     * Deletes {@code recipe} from the list and returns a new PlannedDate.
     */
    public PlannedDate deleteRecipe(Recipe recipe) {
        List<Recipe> newRecipes = new ArrayList<>(recipes);
        newRecipes.remove(recipe);
        return new PlannedDate(newRecipes, date);
    }

    /**
     * Returns true if the size of recipes is 1.
     */
    public boolean isOneRecipe() {
        return recipes.size() == 1;
    }

    /**
     * Returns true if {@code plannedDate} is planned on the same day and all its recipes
     * can be found in the current PlannedDate's list, or if duplicate recipes are found in {@code plannedDate}.
     */
    public boolean hasSameRecipeInPlan(PlannedDate plannedDate) {
        if (plannedDate.recipesAreUnique()) {
            return date.equals(plannedDate.date) && recipes.containsAll(plannedDate.getRecipes());
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

    public boolean hasSameDate(PlannedDate other) {
        return date.equals(other.date);
    }

    public Date getDate() {
        return date;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public int compareTo(PlannedDate other) {
        return date.compareTo(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes, date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedDate // instanceof handles nulls
                && date.equals(((PlannedDate) other).date) // state check
                && recipes.equals(((PlannedDate) other).recipes));
    }

    @Override
    public String toString() {
        return date.toString() + "\n" + recipes.toString();
    }

}
