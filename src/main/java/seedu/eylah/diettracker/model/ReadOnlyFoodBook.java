package seedu.eylah.diettracker.model;

import javafx.collections.ObservableList;
import seedu.eylah.diettracker.model.food.Food;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFoodBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Food> getFoodList();

}
