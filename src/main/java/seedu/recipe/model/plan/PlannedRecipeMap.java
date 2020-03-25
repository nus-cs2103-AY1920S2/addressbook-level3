package seedu.recipe.model.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.recipe.model.recipe.Recipe;

/**
 * Holds a tree of (Date, List(Recipe)) (key, value) pairs which stores the
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
    public List<Recipe> getRecipeAt(Date date) {
        return plannedRecipes.get(date);
    }

    /**
     * Returns a list of recipes that were planned from the period starting from date
     * to 'daysFromDate' number of days since that day.
     */
    /*    public List<Recipe> getRecipeInRange(Date date, int daysFromDate) {
        List<Recipe> recipes = new ArrayList<>();
        while (daysFromDate > 0 && hasLaterDate(date)) {
            // check if later date is more than days from date
            date = plannedRecipes.higherKey(date);
            recipes.addAll(getRecipeAt(date));
            // subtract jump and update days from date
        }
    }*/

    public boolean hasLaterDate(Date date) {
        return plannedRecipes.higherKey(date) != null;
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
