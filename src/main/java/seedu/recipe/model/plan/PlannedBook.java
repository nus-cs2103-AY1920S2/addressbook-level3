package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;

/**
 * Wraps all data at the planned-recipe-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 * //todo, shift the Map to another class
 */
public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedList plannedRecipes;
    private final Map<Recipe, List<PlannedRecipe>> plannedRecipeMap;

    public PlannedBook() {
        plannedRecipes = new UniquePlannedList();
        plannedRecipeMap = new HashMap<>();
    }

    /**
     * Creates a PlannedBook using the planned recipes in the {@code toBeCopied}
     */
    public PlannedBook(ReadOnlyPlannedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the planned recipe list with {@code plannedRecipes}.
     * {@code plannedRecipes} must not contain duplicate recipes on the same day.
     */
    public void setPlannedRecipes(ObservableList<PlannedRecipe> plannedRecipes) {
        this.plannedRecipes.setPlannedRecipes(plannedRecipes);
    }

    /**
     * Replaces the contents of the planned recipe to recipe mapping with {@code plannedRecipes}.
     */
    public void setPlannedRecipeMap(Map<Recipe, List<PlannedRecipe>> plannedRecipeMap) {
        this.plannedRecipeMap.clear();
        this.plannedRecipeMap.putAll(plannedRecipeMap);
    }

    /**
     * Resets the existing data of this {@code PlannedBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPlannedBook newData) {
        requireNonNull(newData);

        setPlannedRecipes(newData.getPlannedList());
        setPlannedRecipeMap(newData.getPlannedRecipeMap());
    }

    // ===== PlannedRecipe-level methods =====

    /**
     * Checks whether the planned book contains {@code plannedRecipe}.
     */
    public boolean contains(PlannedRecipe plannedRecipe) {
        return plannedRecipes.hasPlannedRecipe(plannedRecipe);
    }

    /**
     * Adds a {@code plannedRecipe} into plannedRecipes.
     * The planned recipe must not exist in the planned book.
     * Adds the mapping from {@code recipe} to {@code plannedRecipe} as well.
     */
    public void addPlanForOneRecipe(Recipe recipe, PlannedRecipe plannedRecipe) {
        plannedRecipes.add(plannedRecipe);
        addPlannedMapping(recipe, plannedRecipe);
    }

    /**
     * Adds a {@code plannedRecipe} into plannedRecipes.
     * The planned recipe must not exist in the planned book.
     * Adds the mapping from all {@code recipe} to {@code plannedRecipe} as well.
     */
    public void addPlanForAllRecipes(List<Recipe> recipes, PlannedRecipe plannedRecipe) {
        plannedRecipes.add(plannedRecipe);
        addAllPlannedMapping(recipes, plannedRecipe);
    }

    /**
     * Deletes {@code recipe} from all planned recipes.
     * Deletes all the {@code recipe} key in the mapping as well.
     */
    public void deleteAllPlansFor(Recipe recipe) {
        if (plannedRecipeMap.containsKey(recipe)) {
            List<PlannedRecipe> plannedRecipesForRecipe = plannedRecipeMap.get(recipe);
            for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
                deleteRecipeFromPlannedRecipe(recipe, plannedRecipe);
            }
            plannedRecipeMap.remove(recipe);
        }
    }

    /**
     * Deletes one {@code recipe} from the internal list in the {@code plannedRecipe}.
     * If the {@code recipe} is the last recipe in the internal list, delete the {@code plannedRecipe}
     * from the plannedRecipes list.
     * Deletes the mapping from {@code recipe} to {@code plannedRecipe} in the mapping as well.
     */
    public void deleteRecipeFromPlannedRecipe(Recipe recipe, PlannedRecipe plannedRecipe) {
        deleteOnePlannedMapping(recipe, plannedRecipe); // deletes mapping
        int sizeOfInternalList = plannedRecipe.getRecipes().size();
        if (sizeOfInternalList == 1) { // recipe to be deleted is the last recipe
            deletePlannedRecipe(plannedRecipe);
        } else {
            plannedRecipe.getRecipes().remove(recipe);
        }
    }

    /**
     * Updates the recipe in the planned recipes from {@code target} to {@code editedRecipe}.
     */
    public void setRecipeInPlans(Recipe target, Recipe editedRecipe) {
        if (plannedRecipeMap.containsKey(target)) {
            List<PlannedRecipe> plannedRecipesForRecipe = plannedRecipeMap.get(target);
            plannedRecipeMap.remove(target);
            for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
                plannedRecipe.setRecipe(target, editedRecipe);
            }
            plannedRecipeMap.put(editedRecipe, plannedRecipesForRecipe);
        }
    }

    /**
     * Deletes the {@code plannedRecipe} with all its internal recipes from the plannedRecipes list.
     */
    private void deletePlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedRecipes.remove(plannedRecipe);
    }

    // ===== Private mapping methods =====

    /**
     * Adds a {@code plannedRecipe} to the key {@code recipe} in the mapping of Recipe to PlannedRecipe.
     */
    private void addPlannedMapping(Recipe recipe, PlannedRecipe plannedRecipe) {
        if (plannedRecipeMap.containsKey(recipe)) {
            plannedRecipeMap.get(recipe).add(plannedRecipe);
        } else {
            List<PlannedRecipe> plannedRecipes = new ArrayList<>();
            plannedRecipes.add(plannedRecipe);
            plannedRecipeMap.put(recipe, plannedRecipes);
        }
    }

    /**
     * Adds {@code plannedRecipe} to all the keys in {@code recipes} in the mapping of Recipe to PlannedRecipe.
     */
    private void addAllPlannedMapping(List<Recipe> recipes, PlannedRecipe plannedRecipe) {
        for (Recipe recipe : recipes) {
            if (plannedRecipeMap.containsKey(recipe)) {
                plannedRecipeMap.get(recipe).add(plannedRecipe);
            } else {
                List<PlannedRecipe> plannedRecipes = new ArrayList<>();
                plannedRecipes.add(plannedRecipe);
                plannedRecipeMap.put(recipe, plannedRecipes);
            }
        }
    }

    /**
     * Deletes one {@code plannedRecipe} in the list of planned recipes at the {@code recipe} key.
     */
    private void deleteOnePlannedMapping(Recipe recipe, PlannedRecipe plannedRecipe) {
        plannedRecipeMap.get(recipe).remove(plannedRecipe);
    }

    // ===== Util methods =====

    @Override
    public ObservableList<PlannedRecipe> getPlannedList() {
        return plannedRecipes.asUnmodifiableObservableList();
    }

    @Override
    public Map<Recipe, List<PlannedRecipe>> getPlannedRecipeMap() {
        return plannedRecipeMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedBook // instanceof handles nulls
                && plannedRecipes.equals(((PlannedBook) other).plannedRecipes));
    }

    @Override
    public int hashCode() {
        return plannedRecipes.hashCode();
    }

}
