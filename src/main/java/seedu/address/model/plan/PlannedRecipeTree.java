package seedu.address.model.plan;

import java.time.LocalDate;
import java.util.TreeMap;

import seedu.address.model.recipe.Recipe;

/**
 * Holds a tree of <LocalDate, Recipe> (key, value) pairs which stores the
 * planned recipe that the user plans to cook on that certain date.
 *
 * The value of each key (date) currently holds only one recipe.
 */
public class PlannedRecipeTree {

    private TreeMap<LocalDate, Recipe> plannedRecipes;

    public PlannedRecipeTree() {
        plannedRecipes = new TreeMap<>();
    }

    /**
     * Adds a planned recipe to a date in the tree.
     */
    public void add(Recipe toAdd, LocalDate date) {
        plannedRecipes.put(date, toAdd);
    }

    /**
     * Gets the recipe planned at a specific date.
     * If no recipes were planned on that day, return null.
     */
    public Recipe get(LocalDate date) {
        return plannedRecipes.get(date);
    }

    /**
     * Removes all recipes planned on the specified day.
     */
    public void remove(LocalDate date) {
        plannedRecipes.remove(date);
    }
}
