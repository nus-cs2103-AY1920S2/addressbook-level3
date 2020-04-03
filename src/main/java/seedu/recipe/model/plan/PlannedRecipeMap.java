package seedu.recipe.model.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.recipe.model.recipe.Recipe;

/**
 * A map of recipe to list-of-planned-recipes that contains mappings to ease the updating of planned recipes when
 * the original recipes are changed.
 */
public class PlannedRecipeMap {

    private Map<Recipe, List<PlannedDate>> internalMap;

    public PlannedRecipeMap() {
        internalMap = new HashMap<>();
    }

    /**
     * Replaces the contents of the map with {@code plannedRecipeMap}.
     */
    public void setPlannedRecipeMap(PlannedRecipeMap plannedRecipeMap) {
        internalMap.clear();
        internalMap.putAll(plannedRecipeMap.getInternalMap());
    }

    /**
     * Adds the {@code plannedDate} to the list of planned recipes at {@code recipe}.
     */
    public void addOnePlannedRecipe(Recipe recipe, PlannedDate plannedDate) {
        if (internalMap.containsKey(recipe)) {
            internalMap.get(recipe).add(plannedDate);
        } else {
            List<PlannedDate> allPlans = new ArrayList<>();
            allPlans.add(plannedDate);
            internalMap.put(recipe, allPlans);
        }
    }

    /**
     * Adds all {@code plannedDates} to the {@code recipe} key.
     */
    public void addAllPlannedRecipes(Recipe recipe, List<PlannedDate> plannedDates) {
        if (internalMap.containsKey(recipe)) {
            internalMap.get(recipe).addAll(plannedDates);
        } else {
            internalMap.put(recipe, plannedDates);
        }
    }

    /**
     * Adds the {@code plannedDate} to all {@code recipes} keys
     */
    public void addAllRecipesToPlan(List<Recipe> recipes, PlannedDate plannedDate) {
        recipes.stream().forEach(recipe -> addOnePlannedRecipe(recipe, plannedDate));
    }


    /**
     * Deletes the {@code plannedDate} from the list at the {@code recipe} key.
     */
    public void deleteOnePlannedRecipe(Recipe recipe, PlannedDate plannedDate) {
        List<PlannedDate> allPlans = internalMap.get(recipe);
        if (allPlans.size() == 1) { // only one plan in this list
            internalMap.remove(recipe);
        } else {
            allPlans.remove(plannedDate);
        }
    }

    /**
     * Deletes the list of planned recipes at {@code recipe} key.
     */
    public void deleteAllPlannedRecipes(Recipe recipe) {
        internalMap.remove(recipe);
    }

    /**
     * Returns a new list of planned dates at the {@code recipe} key.
     */
    public List<PlannedDate> getPlans(Recipe recipe) {
        if (contains(recipe)) {
            return new ArrayList<>(internalMap.get(recipe));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Removes the values at {@code target} key and places the updated values at the {@code editedRecipe} key.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe, List<PlannedDate> newPlans) {
        if (internalMap.containsKey(target)) {
            internalMap.remove(target);
            internalMap.put(editedRecipe, newPlans);
        }
    }

    /**
     * Returns true if map contains {@code key}.
     */
    public boolean contains(Recipe key) {
        return internalMap.containsKey(key);
    }

    /**
     * Returns a new copy of the recipe to list-of-planned-recipes mapping.
     */
    private Map<Recipe, List<PlannedDate>> getInternalMap() {
        return new HashMap<>(internalMap);
    }
}
