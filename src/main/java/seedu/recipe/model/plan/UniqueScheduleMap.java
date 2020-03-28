package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * Holds a tree of (Date, List(Recipe)) (key, value) pairs which stores the
 * planned recipe that the user plans to cook on that certain date.
 *
 * The value of each key (date) currently holds only one recipe.
 */
public class UniqueScheduleMap {

    //private TreeMap<Recipe, List<Date>> plannedDates; // store this in Model instead,
    private ObservableMap<Date, List<Recipe>> observableMap = FXCollections.observableMap(new TreeMap<>());
    private ObservableMap<Date, List<Recipe>> unmodifiableObservableMap = FXCollections
            .unmodifiableObservableMap(observableMap);

    // todo: add contains boolean check, and before adding, check if duplicate recipe on the same day is present.
    // if it is, throw exception.

    /**
     * Replaces the contents of this map with {@code scheduledRecipes}.
     * {@code scheduledRecipes} must not contain duplicate recipes on the same date.
     */
    public void setScheduledRecipes(Map<Date, List<Recipe>> scheduledRecipes) {
        requireAllNonNull(scheduledRecipes);
        /*if (!scheduledRecipesAreUnique(scheduledRecipes)) { todo later
            throw new DuplicateScheduledRecipeException();
        }*/

        observableMap.clear();
        observableMap.putAll(scheduledRecipes);
    }

    /**
     * Adds a planned recipe to a date in the tree.
     */
    public void add(Recipe toAdd, Date date) {
        requireNonNull(toAdd);
        requireNonNull(date);
        if (observableMap.containsKey(date)) {
            observableMap.get(date).add(toAdd);
        } else {
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(toAdd);
            observableMap.put(date, recipes);
        }
    }

    /**
     * Removes all recipes planned on the specified day.
     * If date does not exist, throw an error
     */
    public void remove(Date date) {
        requireNonNull(date);
        if (observableMap.remove(date) == null) {
            throw new DateNotFoundException();
        }
    }

    /**
     * Gets the recipes planned at a specific date.
     * If no recipes were planned on that day, return null.
     */
    /*public List<Recipe> getRecipeAt(Date date) {
        return plannedRecipes.get(date);
    }*/

    /**
     * Returns a list of recipes that were planned from the period starting from date
     * to 'daysFromDate' number of days since that day.
     */
    /*    public List<Recipe> getRecipeInRange(Date date, int daysFromDate) {
        List<Recipe> recipes = new ArrayList<>();
        while (daysFromDate > 0 && hasLaterDate(date)) {
            // check if later date is more than days from date
            date = plannedRecipes.higherKey(date);
            recipes.addAll(getRecipeAt(date));
            // subtract jump and update days from date
        }
    }*/

    /*public boolean hasLaterDate(Date date) {
        return plannedRecipes.higherKey(date) != null;
    }*/

    /**
     * Gets all recipes that were planned.
     * If no recipes were planned, return an empty string.
     */
    /*public String getScheduled() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Date, List<Recipe>> entry : plannedRecipes.entrySet()) {
            Date date = entry.getKey();
            List<Recipe> recipes = entry.getValue();
            sb.append(date.toString())
                    .append(System.getProperty("line.separator"));
            recipes.forEach(recipe -> sb.append(recipe));
        }
        return sb.toString();
    }*/

    /**
     * Returns the map as an unmodifiable {@code ObservableMap}.
     */
    public ObservableMap<Date, List<Recipe>> asUnmodifiableObservableMap() {
        return unmodifiableObservableMap;
    }

    @Override
    public int hashCode() {
        return observableMap.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduleMap // instanceof handles nulls
                && observableMap.equals(((UniqueScheduleMap) other).observableMap));
    }
}
