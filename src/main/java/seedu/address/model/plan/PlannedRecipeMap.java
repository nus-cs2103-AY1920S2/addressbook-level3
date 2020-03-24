package seedu.address.model.plan;

import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.recipe.Recipe;

/**
 * Holds a tree of <Date, Recipe> (key, value) pairs which stores the
 * planned recipe that the user plans to cook on that certain date.
 *
 * The value of each key (date) currently holds only one recipe.
 */
public class PlannedRecipeMap {

    private TreeMap<Date, Recipe> plannedRecipes;

    public PlannedRecipeMap() {
        plannedRecipes = new TreeMap<>();
    }

    /**
     * Adds a planned recipe to a date in the tree.
     */
    public void add(Recipe toAdd, Date date) {
        plannedRecipes.put(date, toAdd);
    }

    /**
     * Gets the recipe planned at a specific date.
     * If no recipes were planned on that day, return null.
     */
    public Recipe get(Date date) {
        return plannedRecipes.get(date);
    }

    /**
     * Removes all recipes planned on the specified day.
     */
    public void remove(Date date) {
        plannedRecipes.remove(date);
    }

    /**
     * Gets all recipes that were planned.
     * If no recipes were planned, return an empty string.
     */
    public String getScheduled() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Date, Recipe> entry : plannedRecipes.entrySet()) {
            Date date = entry.getKey();
            Recipe recipe = entry.getValue();
            sb.append(date.toString())
                    .append(System.getProperty("line.separator"))
                    .append(recipe);
        }
        return sb.toString();
    }
}
