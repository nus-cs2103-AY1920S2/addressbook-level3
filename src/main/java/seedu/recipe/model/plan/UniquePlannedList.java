package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class UniquePlannedList {

    private ObservableList<PlannedRecipe> observableList = FXCollections.observableArrayList();
    private ObservableList<PlannedRecipe> unmodifiableObservableList = FXCollections
            .unmodifiableObservableList(observableList);

    // todo: add contains boolean check, and before adding, check if duplicate recipe on the same day is present.
    // if it is, throw exception.

    /**
     * Replaces the contents of this map with {@code plannedRecipes}.
     * {@code plannedRecipes} must not contain duplicate recipes on the same date.
     */
    public void setPlannedRecipes(ObservableList<PlannedRecipe> plannedRecipes) {
        requireAllNonNull(plannedRecipes);
        /*if (!scheduledRecipesAreUnique(scheduledRecipes)) { todo later
            throw new DuplicateSchedulfilteredRecipeException();
        }*/

        observableList.clear();
        observableList.setAll(plannedRecipes);
    }

    /**
     * Adds a planned recipe to the list
     */
    public void add(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        // todo: check duplicate

        observableList.add(plannedRecipe);
    }

    /**
     * Removes the planned recipe
     */
    public void remove(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        if (!observableList.remove(plannedRecipe)) {
            throw new DateNotFoundException();
        }
    }

    /**
     * Returns the list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PlannedRecipe> asUnmodifiableObservableList() {
        return unmodifiableObservableList;
    }

    @Override
    public int hashCode() {
        return observableList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlannedList // instanceof handles nulls
                && observableList.equals(((UniquePlannedList) other).observableList));
    }
}
