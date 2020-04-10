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

    private Map<Recipe, List<Plan>> internalMap;

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

    public void addPlan(Recipe recipe, Plan plan) {
        if (internalMap.containsKey(recipe)) {
            internalMap.get(recipe).add(plan);
        } else {
            List<Plan> plans = new ArrayList<>();
            plans.add(plan);
            internalMap.put(recipe, plans);
        }
    }

    public void deletePlan(Recipe recipe, Plan plan) {
        if (internalMap.containsKey(recipe)) {
            List<Plan> plans = internalMap.get(recipe);
            if (plans.size() == 1) {
                internalMap.remove(recipe);
            } else {
                plans.remove(plan);
            }
        }
    }

    /**
     * Deletes the list of plans at {@code recipe} key.
     */
    public void deleteRecipe(Recipe recipe) {
        if (internalMap.containsKey(recipe)) { // todo check first?
            internalMap.remove(recipe);
        }
    }

    /**
     * Removes the values at {@code target} key and places the updated values at the {@code editedRecipe} key.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe, List<Plan> newPlans) {
        if (internalMap.containsKey(target)) {
            internalMap.remove(target);
            internalMap.put(editedRecipe, newPlans);
        }
    }

    /**
     * Returns a new list of plans at the {@code recipe} key.
     * If {@code recipe} does not exist, return a new list.
     */
    public List<Plan> getPlans(Recipe recipe) {
        if (contains(recipe)) {
            return new ArrayList<>(internalMap.get(recipe));
        } else {
            return new ArrayList<>(); // or check before calling this? todo
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
    public Map<Recipe, List<Plan>> getInternalMap() {
        return new HashMap<>(internalMap);
    }

}
