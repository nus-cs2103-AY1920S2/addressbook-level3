package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;

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
     * Adds a recipe to a date in the planned book.
     * The recipe must not already exist on that day in the planned book.
     */
    public void addPlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedRecipes.add(plannedRecipe);
    }

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
     * Removes all recipes at {@code date} from this {@code PlannedBook}.
     * {@code date} must exist in the planned book.
     */
    public void removePlannedRecipes(PlannedRecipe plannedRecipe) {
        plannedRecipes.remove(plannedRecipe);
    }

    /**
     * params must exist.
     */
    public void removeAllPlannedMappingForRecipe(Recipe recipe) {
        List<PlannedRecipe> plannedRecipesForRecipe = recipeToPlannedRecipeMap.get(recipe);
        for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
            removePlannedRecipes(plannedRecipe);
        }
        recipeToPlannedRecipeMap.remove(recipe);
    }

    public void setPlannedRecipe(Recipe target, Recipe editedRecipe) {
        List<PlannedRecipe> plannedRecipesForRecipe = recipeToPlannedRecipeMap.get(target);
        recipeToPlannedRecipeMap.remove(target);
        for (PlannedRecipe plannedRecipe : plannedRecipesForRecipe) {
            plannedRecipe.setRecipe(editedRecipe);
            //addPlannedMapping(editedRecipe, plannedRecipe);
            System.out.println(recipeToPlannedRecipeMap);
        }
        recipeToPlannedRecipeMap.put(editedRecipe, plannedRecipesForRecipe);
        //removeAllPlannedMappingForRecipe(target);
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
