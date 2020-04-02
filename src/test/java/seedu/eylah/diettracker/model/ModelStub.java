package seedu.eylah.diettracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements DietModel {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getFoodBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFoodBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFood(Food food) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFoodBook(ReadOnlyFoodBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyFoodBook getFoodBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasFood(Food food) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFood(Food target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFood(Food target, Food editedFood) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Food> getFilteredFoodList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void listFoods(String mode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHeight(Height height) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setWeight(Weight weight) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMode(Mode mode) {
        throw new AssertionError("This method should not be called.");
    }
}
