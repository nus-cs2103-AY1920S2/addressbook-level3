package seedu.eylah.diettracker.model;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.UniqueFoodList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFood comparison)
 */
public class FoodBook implements ReadOnlyFoodBook {

    private final UniqueFoodList foods;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foods = new UniqueFoodList();
    }

    public FoodBook() {}

    /**
     * Creates a FoodBook using the Foods in the {@code toBeCopied}
     */
    public FoodBook(ReadOnlyFoodBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the food list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoods(List<Food> foods) {
        this.foods.setFoods(foods);
    }

    /**
     * Resets the existing data of this {@code FoodBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFoodBook newData) {
        requireNonNull(newData);

        setFoods(newData.getFoodList());
    }

    //// food-level operations

    /**
     * Returns true if a food with the same identity as {@code food} exists in the food book.
     */
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foods.contains(food);
    }

    /**
     * Adds a food to the food book.
     * The food must not already exist in the food book.
     */
    public void addFood(Food food) {
        foods.add(food);
    }

    /**
     * Replaces the given food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the food book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the food book.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);

        foods.setFood(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code FoodBook}.
     * {@code key} must exist in the food book.
     */
    public void removeFood(Food key) {
        foods.remove(key);
    }

    //// util methods

    public UniqueFoodList getFoods() {
        return this.foods;
    }

    @Override
    public String toString() {
        return Arrays.toString(foods.asUnmodifiableObservableList().toArray());
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foods.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodBook // instanceof handles nulls
                && foods.equals(((FoodBook) other).getFoods()));
    }

    @Override
    public int hashCode() {
        return foods.hashCode();
    }
}
