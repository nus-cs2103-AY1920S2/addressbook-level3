package seedu.eylah.diettracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.eylah.commons.core.GuiSettings;
import seedu.eylah.diettracker.model.food.Food;

/**
 * Temp method description.
 */
public class ModelManager implements Model {

    public ModelManager() {

    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return null;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

    }

    @Override
    public Path getFoodBookFilePath() {
        return null;
    }

    @Override
    public void setFoodBookFilePath(Path foodBookFilePath) {

    }

    @Override
    public void setFoodBook(ReadOnlyFoodBook foodBook) {

    }

    @Override
    public ReadOnlyFoodBook getFoodBook() {
        return null;
    }

    @Override
    public boolean hasFood(Food food) {
        return false;
    }

    @Override
    public void deleteFood(Food target) {

    }

    @Override
    public void addFood(Food food) {

    }

    @Override
    public void setFood(Food target, Food editedFood) {

    }

    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return null;
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {

    }
}
