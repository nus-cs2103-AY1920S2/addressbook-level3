package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of planned recipes that enforces uniqueness between its elements and does not allow nulls.
 * Ensures that at any point in time, the items in the list of planned recipes each has a unique PlannedDate.
 * This means that PlannedRecipe items with the same PlannedDate must not exist in the list.
 *
 * A planned recipe is considered unique by comparing using {@code PlannedRecipe#equals(object)}.
 *
 *  Supports a minimal set of list operations.
 */
public class UniquePlannedList {

    private ObservableList<PlannedRecipe> internalList = FXCollections.observableArrayList();
    private ObservableList<PlannedRecipe> unmodifiableObservableList = FXCollections
            .unmodifiableObservableList(internalList);

    // todo: check duplicate if it is, throw exception.

    /**
     * Replaces the contents of this map with {@code plannedRecipes}.
     * {@code plannedRecipes} must not contain duplicate recipes on the same date.
     */
    public void setPlannedRecipes(ObservableList<PlannedRecipe> plannedRecipes) {
        requireAllNonNull(plannedRecipes);
        if (!plannedRecipesAreUnique(plannedRecipes)) {
            throw new DuplicatePlannedRecipeException();
        }

        internalList.clear();
        internalList.setAll(plannedRecipes);
    }

    /**
     * Adds a planned recipe to the list
     */
    public void add(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        if (internalList.contains(plannedRecipe)) {
            throw new DuplicatePlannedRecipeException();
        }

        int indexOfSameDate = indexOfPlannedRecipeWithSameDate(plannedRecipe);
        if (indexOfSameDate == -1) {
            internalList.add(plannedRecipe);
        } else {
            PlannedRecipe currentPlannedRecipe = internalList.get(indexOfSameDate)
                    .plannedRecipeWithAllPlanned(plannedRecipe);
            internalList.set(indexOfSameDate, currentPlannedRecipe);
        }
    }

    /**
     * Removes the planned recipe
     */
    public void remove(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        if (!internalList.remove(plannedRecipe)) {
            throw new PlannedRecipeNotFoundException();
        }
    }

    /**
     * Returns the index of a planned recipe with the same date as {@code otherPlannedRecipe}.
     */
    public int indexOfPlannedRecipeWithSameDate(PlannedRecipe otherPlannedRecipe) {
        PlannedDate otherDate = otherPlannedRecipe.getDate();
        for (int i = 0; i < internalList.size(); i++) {
            PlannedDate currentDate = internalList.get(i).getDate();
            if (currentDate.equals(otherDate)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if {@code plannedRecipes} contains only unique recipes.
     */
    private boolean plannedRecipesAreUnique(List<PlannedRecipe> plannedRecipes) {
        for (int i = 0; i < plannedRecipes.size() - 1; i++) {
            for (int j = i + 1; j < plannedRecipes.size(); j++) {
                if (plannedRecipes.get(i).equals(plannedRecipes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PlannedRecipe> asUnmodifiableObservableList() {
        return unmodifiableObservableList;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlannedList // instanceof handles nulls
                && internalList.equals(((UniquePlannedList) other).internalList));
    }
}
