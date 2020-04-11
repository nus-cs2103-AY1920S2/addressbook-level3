package seedu.recipe.model.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.recipe.model.recipe.Recipe;

/**
 * A map of recipe to list-of-plans that contains mappings to ease the updating of plans when
 * original recipes are changed.
 */
public class PlannedRecipeMap {

    private final Map<Recipe, List<Plan>> internalMap;

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
     * Adds {@code plan} to the {@code recipe} key.
     */
    public void addPlan(Recipe recipe, Plan plan) {
        if (internalMap.containsKey(recipe)) {
            internalMap.get(recipe).add(plan);
        } else {
            List<Plan> plans = new ArrayList<>();
            plans.add(plan);
            internalMap.put(recipe, plans);
        }
    }

    /**
     * Deletes {@code plan} from the {@code recipe} key if it exists.
     */
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
     * Deletes all plans at {@code recipe} key if it exists.
     */
    public void deleteRecipe(Recipe recipe) {
        if (internalMap.containsKey(recipe)) {
            internalMap.remove(recipe);
        }
    }

    /**
     * Shifts the values from {@code target} key to {@code editedRecipe} key.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe, List<Plan> newPlans) {
        if (internalMap.containsKey(target)) {
            internalMap.remove(target);
            internalMap.put(editedRecipe, newPlans);
        }
    }

    /**
     * Returns an optional new list of plans at the {@code recipe} key.
     * Returns optional empty if {@code recipe} does not exist.
     */
    public Optional<List<Plan>> getPlans(Recipe recipe) {
        if (contains(recipe)) {
            return Optional.of(new ArrayList<>(internalMap.get(recipe)));
        } else {
            return Optional.empty();
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

    /**
     * Returns all entries in the mapping.
     */
    public Set<Map.Entry<Recipe, List<Plan>>> getAllEntries() {
        return internalMap.entrySet();
    }

}
