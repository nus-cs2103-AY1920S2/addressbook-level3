package seedu.eylah.diettracker.model;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eylah.commons.core.GuiSettings;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Self;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FoodBook foodBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Food> filteredFoods;
    private Mode mode = Mode.MAINTAIN;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyFoodBook foodBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(foodBook, userPrefs);

        logger.fine("Initializing with address book: " + foodBook + " and user prefs " + userPrefs);

        this.foodBook = new FoodBook(foodBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoods = new FilteredList<>(this.foodBook.getFoodList());
    }

    public ModelManager() {
        this(new FoodBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFoodBookFilePath() {
        return userPrefs.getFoodBookFilePath();
    }

    @Override
    public void setFoodBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setFoodBookFilePath(addressBookFilePath);
    }

    //=========== FoodBook ================================================================================

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

    @Override
    public String listFoods(String mode, int numDay) {
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return foodBook.equals(other.foodBook)
                && userPrefs.equals(other.userPrefs)
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
