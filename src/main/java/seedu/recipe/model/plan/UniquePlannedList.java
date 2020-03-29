package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Holds a tree of (PlannedDate, List(Recipe)) (key, value) pairs which stores the
 * planned recipe that the user plans to cook on that certain date.
 *
 * The value of each key (date) currently holds only one recipe.
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
            throw new DuplicateScheduledRecipeException();
        }*/

        observableList.clear();
        observableList.setAll(plannedRecipes);
    }

    /**
     * Adds a planned recipe to a date in the tree.
     */
    public void add(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        // todo: check duplicate

        observableList.add(plannedRecipe);
    }

    /**
     * Removes all recipes planned on the specified day.
     * If date does not exist, throw an error
     */
    public void remove(PlannedRecipe plannedRecipe) {
        requireNonNull(plannedRecipe);
        if (!observableList.remove(plannedRecipe)) {
            throw new PlannedRecipeNotFoundException();
        }
    }

/*    public ObservableMap<PlannedDate, ObservableList<Recipe>> getSubMapInRange(PlannedDate start, PlannedDate end) {
        TreeMap<PlannedDate, ObservableList<Recipe>> treeMap = (TreeMap<PlannedDate, ObservableList<Recipe>>) observableMap;
        return FXCollections.observableMap(treeMap.subMap(start, true, end, true));
    }

    public ObservableMap<PlannedDate, ObservableList<Recipe>> getSubMapOfMonth(PlannedDate date) {
        PlannedDate start = date.onFirstDayOfMonth();
        PlannedDate end = date.onLastDayOfMonth();
        return getSubMapInRange(start, end);
    }*/

    /**
     * Gets the recipes planned at a specific date.
     * If no recipes were planned on that day, return null.
     */
    /*public List<Recipe> getRecipeAt(PlannedDate date) {
        return plannedRecipes.get(date);
    }*/

    /**
     * Returns a list of recipes that were planned from the period starting from date
     * to 'daysFromDate' number of days since that day.
     */
    /*    public List<Recipe> getRecipeInRange(PlannedDate date, int daysFromDate) {
        List<Recipe> recipes = new ArrayList<>();
        while (daysFromDate > 0 && hasLaterDate(date)) {
            // check if later date is more than days from date
            date = plannedRecipes.higherKey(date);
            recipes.addAll(getRecipeAt(date));
            // subtract jump and update days from date
        }
    }*/

    /*public boolean hasLaterDate(PlannedDate date) {
        return plannedRecipes.higherKey(date) != null;
    }*/

    /**
     * Gets all recipes that were planned.
     * If no recipes were planned, return an empty string.
     */
    /*public String getScheduled() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<PlannedDate, List<Recipe>> entry : plannedRecipes.entrySet()) {
            PlannedDate date = entry.getKey();
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
