package seedu.eylah.diettracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.eylah.commons.model.Model;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * The API of the Model component.
 */
public interface DietModel extends Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Food> PREDICATE_SHOW_ALL_FOODS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    @Override
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    @Override
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' food book file path.
     */
    Path getFoodBookFilePath();

    /**
     * Sets the user prefs' food book file path.
     */
    void setFoodBookFilePath(Path foodBookFilePath);

    /**
     * Replaces food book data with the data in {@code foodBook}.
     */
    void setFoodBook(ReadOnlyFoodBook foodBook);

    /** Returns the FoodBook */
    ReadOnlyFoodBook getFoodBook();

    /**
     * Returns true if a food with the same identity as {@code food} exists in the food book.
     */
    boolean hasFood(Food food);

    /**
     * Deletes the given food.
     * The food must exist in the food book.
     */
    void deleteFood(Food target);

    /**
     * Adds the given food.
     * {@code food} must not already exist in the food book.
     */
    void addFood(Food food);

    /**
     * Edits the given food.
     * {@code food} must already exist in the food book.
     */
    //void editFood(Food food);

    /**
     * Replaces the given food {@code target} with {@code editedFood}.
     * {@code target} must exist in the food book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the food book.
     */
    void setFood(Food target, Food editedFood);

    /** Returns an unmodifiable view of the filtered food list */
    ObservableList<Food> getFilteredFoodList();

    /** Returns the String format of the foods based on the mode input.
     */
    String listFoods(String mode);


    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);

    void setHeight(Height height);

    void setWeight(Weight weight);

    void setMode(Mode mode);
}
