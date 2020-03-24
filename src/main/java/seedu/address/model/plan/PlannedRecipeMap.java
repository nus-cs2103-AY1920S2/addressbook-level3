package seedu.address.model.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.model.recipe.Recipe;

/**
 * Holds a tree of <Date, List<Recipe>> (key, value) pairs which stores the
 * planned recipe that the user plans to cook on that certain date.
 *
 * The value of each key (date) currently holds only one recipe.
 */
public class PlannedRecipeMap {

    private TreeMap<Date, List<Recipe>> plannedRecipes;

    public PlannedRecipeMap() {
        plannedRecipes = new TreeMap<>();
    }

    /**
     * Adds a planned recipe to a date in the tree.
     */
    public void add(Recipe toAdd, Date date) {
        if (plannedRecipes.containsKey(date)) {
            plannedRecipes.get(date).add(toAdd);
        } else {
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(toAdd);
            plannedRecipes.put(date, recipes);
        }
    }

    /**
     * Gets the recipes planned at a specific date.
     * If no recipes were planned on that day, return null.
     */
    public List<Recipe> get(Date date) {
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
        for (Map.Entry<Date, List<Recipe>> entry : plannedRecipes.entrySet()) {
            Date date = entry.getKey();
            List<Recipe> recipes = entry.getValue();
            sb.append(date.toString())
                    .append(System.getProperty("line.separator"));
            recipes.forEach(recipe -> sb.append(recipe));
        }
        return sb.toString();
    }
}
