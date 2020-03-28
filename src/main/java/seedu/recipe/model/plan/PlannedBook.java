package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.recipe.model.recipe.Recipe;

public class PlannedBook implements ReadOnlyPlannedBook {

    private final UniquePlannedMap plannedRecipes;

    public PlannedBook() {
        plannedRecipes = new UniquePlannedMap();
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
    public void setPlannedRecipes(Map<Date, ObservableList<Recipe>> plannedRecipes) {
        this.plannedRecipes.setPlannedRecipes(plannedRecipes);
    }

    /**
     * Resets the existing data of this {@code PlannedBook} with {@code newData}.
     * @param newData
     */
    public void resetData(ReadOnlyPlannedBook newData) {
        requireNonNull(newData);

        setPlannedRecipes(newData.getPlannedMap());
    }

    // ===== Recipe-level methods =====

    /**
     * Adds a recipe to a date in the planned book.
     * The recipe must not already exist on that day in the planned book.
     */
    public void addPlannedRecipe(Recipe recipe, Date date) {
        plannedRecipes.add(recipe, date);
    }

    /**
     * Removes all recipes at {@code date} from this {@code PlannedBook}.
     * {@code date} must exist in the planned book.
     */
    public void removePlannedRecipes(Date date) {
        plannedRecipes.remove(date);
    }


    // ===== Util methods =====

    @Override
    public ObservableMap<Date, ObservableList<Recipe>> getPlannedMap() {
        return plannedRecipes.asUnmodifiableObservableMap();
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
