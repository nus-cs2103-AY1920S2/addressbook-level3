package seedu.eylah.diettracker.model;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Represents the in-memory model of the address book data.
 */
public class DietModelManager implements DietModel {
    public static final long GAIN_CALORIES = 3000;
    public static final long LOSE_CALORIES = 2000;
    public static final long MAINTAIN_CALORIES = 2500;
    private static final Logger logger = LogsCenter.getLogger(DietModelManager.class);

    private final UserPrefs userPrefs;
    private final FoodBook foodBook;
    private final Myself myself;
    private final FilteredList<Food> filteredFoods;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and myself (user metrics).
     */
    public DietModelManager(ReadOnlyFoodBook foodBook, ReadOnlyUserPrefs userPrefs, ReadOnlyMyself myself) {
        requireAllNonNull(foodBook, userPrefs, myself);

        logger.fine("Initializing with food book: " + foodBook + " and user prefs " + userPrefs
                + "and Myself (user) " + myself);

        this.userPrefs = new UserPrefs(userPrefs);
        this.foodBook = new FoodBook(foodBook);
        filteredFoods = new FilteredList<>(this.foodBook.getFoodList());
        this.myself = new Myself(myself);
    }

    public DietModelManager() {
        this(new FoodBook(), new UserPrefs(), new Myself());
    }

    //=========== Myself ==================================================================================

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
    public Path getMyselfFilePath() {
        return userPrefs.getMyselfFilePath();
    }

    @Override
    public void setMyselfFilePath(Path myselfFilePath) {
        requireNonNull(myselfFilePath);
        userPrefs.setMyselfFilePath(myselfFilePath);
    }

    @Override
    public void setMyself(ReadOnlyMyself myself) {
        this.myself.resetData(myself);
    }

    @Override
    public ReadOnlyMyself getMyself() {
        return myself;
    }

    @Override
    public Height getHeight() {
        return myself.getHeight();
    }

    @Override
    public Weight getWeight() {
        return myself.getWeight();
    }

    @Override
    public void setHeight(Height height) {
        myself.setHeight(height);
    }

    @Override
    public void setWeight(Weight weight) {
        myself.setWeight(weight);
    }

    @Override
    public void setMode(Mode mode) {
        myself.setMode(mode);
    }

    @Override
    public String printMetrics() {
        StringBuilder result = new StringBuilder("Your metrics are as follows:\n\n");

        // checks for stored height, displays height if available.
        if (myself.getHeight().toString().equals("0.0")) {
            result.append("    Height: You have not stored your own height yet! Type 'height <<insert height here>>' "
                    + "to store your height!\n");
        } else {
            result.append("    Height: " + myself.getHeight() + "\n");
        }

        // checks for stored weight, displays weight if available.
        if (myself.getWeight().toString().equals("0.0")) {
            result.append("    Weight: You have not stored your own weight yet! Type 'weight <<insert weight here>>' "
                    + "to store your weight!\n");
        } else {
            result.append("    Weight: " + myself.getWeight() + "\n");
        }

        // checks for stored mode, displays mode if available.
        if (myself.getMode() == null) {
            result.append("    Dieting Mode: You have not chosen your dieting mode yet! Type 'mode <<-l/-m/-g>>' "
                    + "to choose your dieting mode!\n");
        } else {
            result.append("    Dieting Mode: " + myself.getMode() + "\n");
        }

        return result.toString();
    }

    @Override
    public Mode getMode() {
        return myself.getMode();
    }

    //=========== FoodBook ================================================================================

    @Override
    public Path getFoodBookFilePath() {
        return userPrefs.getFoodBookFilePath();
    }

    @Override
    public void setFoodBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setFoodBookFilePath(addressBookFilePath);
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
        Calories calorieCount = new Calories(0);
        StringBuilder result = new StringBuilder();

        if (filteredFoods.isEmpty()) {
            if (mode == null || mode.equals("")) {
                result.append("List is empty for today! Add a -a flag to access your entire diet history.\n");
            } else if (mode.equals("-t")) {
                result.append("List is empty for the given tag!\n");
            } else if (mode.equals("-d")) {
                result.append("List is empty for the given time period!\n");
            } else {
                result.append("You have no food in your entire diet history!\n");
            }
        } else {
            if (mode == null || mode.equals("")) {
                result.append("These are all food that you have consumed for today!\n\n");
            } else if (mode.equals("-d")) {
                result.append("These are the food consumed for the given number of days!\n\n");
            } else if (mode.equals("-t")) {
                result.append("These are all the food consumed of the given tag!\n\n");
            } else { // "-a"
                result.append("These are all food you have ever consumed!\n\n");
            }
        }

        int count = 1;
        for (Food food: filteredFoods) {
            calorieCount = calorieCount.add(food.getCalories());
            result.append(count + ". " + food.toString() + "\n");
            count++;
        }
        result.append("\nTotal Calorie Intake : " + calorieCount + "\n");

        if (mode == null || mode.equals("")) {
            Calories caloriesLeft;
            long calorieIntake;
            if (myself.getMode() == Mode.GAIN) {
                calorieIntake = GAIN_CALORIES;
            } else if (myself.getMode() == Mode.LOSS) {
                calorieIntake = LOSE_CALORIES;
            } else { // MAINTAIN
                calorieIntake = MAINTAIN_CALORIES;
            }

            caloriesLeft = new Calories(calorieIntake);
            if (caloriesLeft.greaterThan(calorieCount)) {
                caloriesLeft = caloriesLeft.difference(calorieCount);
                result.append("\nYou have " + caloriesLeft + " calories left for the day!\n");
            } else if (caloriesLeft.lessThan(calorieCount)) {
                caloriesLeft = caloriesLeft.difference(calorieCount);
                result.append("\nYou have exceeded your daily calorie limit (" + Long.valueOf(calorieIntake)
                        + ") by " + caloriesLeft + " calories!\n");
            } else {
                result.append("\nYou have hit your calorie limit for the day!\n");
            }


        }

        return result.toString();
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
}
