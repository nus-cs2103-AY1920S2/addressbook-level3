package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableMap;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Recipe;

public class ScheduleBook implements ReadOnlyScheduleBook {

    private final UniqueScheduleMap scheduledRecipes;

    public ScheduleBook() {
        scheduledRecipes = new UniqueScheduleMap();
    }

    /**
     * Creates a ScheduleBook using the scheduled recipes in the {@code toBeCopied}
     */
    public ScheduleBook(ReadOnlyScheduleBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    /**
     * Replaces the contents of the scheduled recipe map with {@code scheduledRecipes}.
     * {@code scheduledRecipes} must not contain duplicate recipes on the same day.
     */
    public void setScheduledRecipes(Map<Date, List<Recipe>> scheduledRecipes) {
        this.scheduledRecipes.setScheduledRecipes(scheduledRecipes);
    }

    /**
     * Resets the existing data of this {@code ScheduleBook} with {@code newData}.
     * @param newData
     */
    public void resetData(ReadOnlyScheduleBook newData) {
        requireNonNull(newData);

        setScheduledRecipes(newData.getScheduleMap());
    }

    // ===== Recipe-level methods =====

    /**
     * Adds a recipe to the address book.
     * The recipe must not already exist in the address book.
     */
    public void addRecipe(Recipe recipe, Date date) {
        scheduledRecipes.add(recipe, date);
    }

    /**
     * Removes all recipes at {@code date} from this {@code ScheduleBook}.
     * {@code key} must exist in the schedule book.
     */
    public void removeRecipe(Date date) {
        scheduledRecipes.remove(date);
    }


    // ===== Util methods =====

    @Override
    public ObservableMap<Date, List<Recipe>> getScheduleMap() {
        return scheduledRecipes.asUnmodifiableObservableMap();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleBook // instanceof handles nulls
                && scheduledRecipes.equals(((ScheduleBook) other).scheduledRecipes));
    }

    @Override
    public int hashCode() {
        return scheduledRecipes.hashCode();
    }
}
