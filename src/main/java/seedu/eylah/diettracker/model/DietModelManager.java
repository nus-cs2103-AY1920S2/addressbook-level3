package seedu.eylah.diettracker.model;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.model.ModelManager;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Represents the in-memory model of the address book data.
 */
public class DietModelManager extends ModelManager implements DietModel {
    private static final Logger logger = LogsCenter.getLogger(DietModelManager.class);

    private final FoodBook foodBook;
    private final FilteredList<Food> filteredFoods;
    private Mode mode = Mode.MAINTAIN;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public DietModelManager(ReadOnlyFoodBook foodBook, ReadOnlyUserPrefs userPrefs) {
        super(userPrefs);
        requireAllNonNull(foodBook, userPrefs);

        logger.fine("Initializing with address book: " + foodBook + " and user prefs " + userPrefs);

        this.foodBook = new FoodBook(foodBook);
        filteredFoods = new FilteredList<>(this.foodBook.getFoodList());
    }

    public DietModelManager() {
        this(new FoodBook(), new UserPrefs());
    }

    //=========== FoodBook ================================================================================

    @Override
    public Path getFoodBookFilePath() {
        return super.getUserPrefs().getFoodBookFilePath();
    }

    @Override
    public void setFoodBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        super.getUserPrefs().setFoodBookFilePath(addressBookFilePath);
    }

    @Override
    public void setFoodBook(ReadOnlyFoodBook addressBook) {
        this.foodBook.resetData(foodBook);
    }

    @Override
    public ReadOnlyFoodBook getFoodBook() {
        return foodBook;
    }

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodBook.hasFood(food);
    }

    @Override
    public void deleteFood(Food target) {
        foodBook.removeFood(target);
    }

    @Override
    public void addFood(Food food) {
        foodBook.addFood(food);
        updateFilteredFoodList(PREDICATE_SHOW_ALL_FOODS);
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);

        foodBook.setFood(target, editedFood);
    }

    @Override
    public String listFoods(String mode) {
        String result = "The food list based on the input flag '" + mode + "' is as follows:\n";
        int count = 1;
        for (Food food: filteredFoods) {
            result += count + ". " + food.toString() + "\n";
            count++;
        }
        return result;
    }

    //=========== Filtered Food Book Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return filteredFoods;
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoods.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DietModelManager)) {
            return false;
        }

        // state check
        DietModelManager other = (DietModelManager) obj;
        return foodBook.equals(other.foodBook)
                && filteredFoods.equals(other.filteredFoods);
    }

    //=========== Self ================================================================================

    @Override
    public void setHeight(Height height) {
        Self.setHeight(height);
    }

    @Override
    public void setWeight(Weight weight) {
        Self.setWeight(weight);
    }

    @Override
    public void setMode(Mode mode) {
        Self.setMode(mode);
    }

}
