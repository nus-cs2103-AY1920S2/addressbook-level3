package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * Wraps all data at the planned-recipe-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedList plannedDates;
    private final PlannedRecipeMap recipeMap;

    public PlannedBook() {
        plannedDates = new UniquePlannedList();
        recipeMap = new PlannedRecipeMap();
    }

    /**
     * Creates a PlannedBook using the planned recipes in the {@code toBeCopied}
     */
    public PlannedBook(ReadOnlyPlannedBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the planned recipe list with {@code plannedDates}.
     * {@code plannedDates} must not contain duplicate recipes on the same day.
     */
    public void setPlannedDates(ObservableList<PlannedDate> plannedDates) {
        this.plannedDates.setPlannedDates(plannedDates);
    }

    /**
     * Replaces the contents of the recipe map with {@code plannedRecipeMap}.
     */
    public void setRecipeMap(PlannedRecipeMap recipeMap) {
        this.recipeMap.setPlannedRecipeMap(recipeMap);
    }

    /**
     * Resets the existing data of this {@code PlannedBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPlannedBook newData) {
        requireNonNull(newData);

        setPlannedDates(newData.getPlannedList());
        setRecipeMap(newData.getPlannedMap());
    }

    // ===== PlannedDate-level methods =====

    /**
     * Checks whether the planned book contains {@code plannedDate}.
     */
    public boolean contains(PlannedDate plannedDate) {
        return plannedDates.hasPlannedDate(plannedDate);
    }

    /**
     * Adds {@code plannedDate} into the planned recipes and recipe map.
     * The planned recipe must not exist in the planned book.
     */
    public void addOnePlan(Recipe recipe, PlannedDate plannedDate) {
        plannedDates.add(plannedDate);
        recipeMap.addOnePlannedRecipe(recipe, plannedDate);
    }

    /**
     * Updates the recipe map for all {@code recipes}.
     * Adds {@code plannedDate} into the planned recipes list.
     * The planned recipe must not exist in the planned book.
     */
    public void addAllRecipesToPlan(List<Recipe> recipes, PlannedDate plannedDate) {
        plannedDates.add(plannedDate);
        recipeMap.addAllRecipesToPlan(recipes, plannedDate);
    }

    /**
     * Deletes {@code recipe} from all planned dates in the planned recipes list and map.
     */
    public void deleteAllRecipePlans(Recipe recipe) {
        List<PlannedDate> plans = recipeMap.getPlans(recipe);
        for (PlannedDate plan: plans) {
            plannedDates.remove(plan);
        }
        recipeMap.deleteAllPlannedRecipes(recipe);
    }

    /**
     * Deletes {@code recipe} from the planned recipes internal list in the {@code plannedDate}.
     * If the {@code recipe} is the last recipe in the internal list, delete the {@code plannedDate}
     * from the plannedRecipes list.
     * Deletes {@code recipe} in the mapping as well.
     */
    public void deleteOnePlan(Recipe recipe, PlannedDate plannedDate) {
        if (plannedDate.isOneRecipe()) { // if one recipe is left, remove plannedDate
            plannedDates.remove(plannedDate);
        } else {
            plannedDate.deleteRecipe(recipe);
        }
        recipeMap.deleteOnePlannedRecipe(recipe, plannedDate);
    }

    /**
     * Sets {@code target} to {@code editedRecipe} in the list and map.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        List<PlannedDate> plans = recipeMap.getPlans(target);
        for (PlannedDate plan: plans) {
            plan.setRecipe(target, editedRecipe);
        }
        recipeMap.setRecipe(target, editedRecipe);
    }


    /*public void favouriteRecipe(Recipe target) {
        List<PlannedDate> plannedTarget = plannedRecipeMap.get(target);
    }*/

    // ===== Util methods =====

    @Override
    public ObservableList<PlannedDate> getPlannedList() {
        return plannedDates.asUnmodifiableObservableList();
    }

    @Override
    public PlannedRecipeMap getPlannedMap() {
        return recipeMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlannedBook // instanceof handles nulls
                && plannedDates.equals(((PlannedBook) other).plannedDates));
    }

    @Override
    public int hashCode() {
        return plannedDates.hashCode();
    }

}
