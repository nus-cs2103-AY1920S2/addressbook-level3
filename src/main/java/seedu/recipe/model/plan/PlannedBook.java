package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedList plannedRecipes;

    public PlannedBook() {
        plannedRecipes = new UniquePlannedList();
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
    }

    // ===== Recipe-level methods =====

    /**
     * Adds a recipe to a date in the planned book.
     * The recipe must not already exist on that day in the planned book.
     */
    public void addPlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedRecipes.add(plannedRecipe);
    }

    /**
     * Removes all recipes at {@code date} from this {@code PlannedBook}.
     * {@code date} must exist in the planned book.
     */
    public void removePlannedRecipes(PlannedRecipe plannedRecipe) {
        plannedRecipes.remove(plannedRecipe);
    }

    // ===== Util methods =====

    @Override
    public ObservableList<PlannedRecipe> getPlannedList() {
        return plannedRecipes.asUnmodifiableObservableList();
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
