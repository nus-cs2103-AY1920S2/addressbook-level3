package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;

/**
 * Wraps all data at the planned-recipe-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 * //todo, shift the Map to another class
 */
public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedList plannedRecipes;
    private Map<Recipe, List<PlannedRecipe>> recipeToPlannedRecipeMap;

    public PlannedBook() {
        plannedRecipes = new UniquePlannedList();
        recipeToPlannedRecipeMap = new HashMap<>();
    }

    /**
     * Creates a PlannedBook using the planned recipes in the {@code toBeCopied}
     */
    public PlannedBook(ReadOnlyPlannedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the planned recipe map with {@code plannedRecipes}.
     * {@code plannedRecipes} must not contain duplicate recipes on the same day.
     */
    public void setPlannedRecipes(ObservableList<PlannedRecipe> plannedRecipes) {
        this.plannedRecipes.setPlannedRecipes(plannedRecipes);
    }


    /**
     * Resets the existing data of this {@code PlannedBook} with {@code newData}.
     * @param newData
     */
    public void resetData(ReadOnlyPlannedBook newData) {
        requireNonNull(newData);

        setPlannedRecipes(newData.getPlannedList());
        recipeToPlannedRecipeMap = newData.getRecipeToPlannedRecipeMap();
    }

    // ===== Recipe-level methods =====

    /**
     * Adds a {@code plannedRecipe} into the planned book.
     * The planned recipe must not exist in the planned book. todo
     */
    public void addPlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedRecipes.add(plannedRecipe);
    }

    /**
     * Adds a {@code plannedRecipe} to the mapping of Recipe to PlannedRecipe.
     */
    public void addPlannedMapping(Recipe recipe, PlannedRecipe plannedRecipe) {
        if (recipeToPlannedRecipeMap.containsKey(recipe)) {
            recipeToPlannedRecipeMap.get(recipe).add(plannedRecipe);
        } else {
            List<PlannedRecipe> plannedRecipes = new ArrayList<>();
            plannedRecipes.add(plannedRecipe);
            recipeToPlannedRecipeMap.put(recipe, plannedRecipes);
        }
    }


    /**
     * Removes the planned recipe from the plannedRecipe list.
     */
    public void removePlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedRecipes.remove(plannedRecipe);
    }

    /**
     * Removes all planned recipes on this {@code recipe} key in the mapping from recipe to planned recipe.
     */
    public void removeAllPlannedMappingForRecipe(Recipe recipe) {
        if (recipeToPlannedRecipeMap.containsKey(recipe)) {
            List<PlannedRecipe> plannedRecipesForRecipe = recipeToPlannedRecipeMap.get(recipe);
            for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
                removePlannedRecipe(plannedRecipe);
            }
            recipeToPlannedRecipeMap.remove(recipe);
        }
    }

    /**
     * Shifts the planned recipes on this {@code target} key to the {@code editedRecipe} key in the mapping from
     * recipe to planned recipe.
     * Updates the Recipe referenced in each PlannedRecipe.
     */
    public void setPlannedRecipe(Recipe target, Recipe editedRecipe) {
        if (recipeToPlannedRecipeMap.containsKey(target)) {
            List<PlannedRecipe> plannedRecipesForRecipe = recipeToPlannedRecipeMap.get(target);
            recipeToPlannedRecipeMap.remove(target);
            for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
                plannedRecipe.setRecipe(target, editedRecipe);
            }
            recipeToPlannedRecipeMap.put(editedRecipe, plannedRecipesForRecipe);
        }
    }

    // ===== Util methods =====

    @Override
    public ObservableList<PlannedRecipe> getPlannedList() {
        return plannedRecipes.asUnmodifiableObservableList();
    }

    @Override
    public Map<Recipe, List<PlannedRecipe>> getRecipeToPlannedRecipeMap() {
        return recipeToPlannedRecipeMap;
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
