package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.Mode;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;
import seedu.eylah.diettracker.testutil.FoodBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFoodAdded modelStub = new ModelStubAcceptingFoodAdded();
        Food validFood = new FoodBuilder().build();

        CommandResult commandResult = new AddCommand(validFood).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFood), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFood), modelStub.foodsAdded);
    }

    @Test
    public void execute_duplicateFood_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddCommand addCommand = new AddCommand(validFood);
        ModelStub modelStub = new ModelStubWithFood(validFood);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FOOD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food alice = new FoodBuilder().withName("Alice").build();
        Food bob = new FoodBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different food -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements DietModel {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMyselfFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMyselfFilePath(Path myselfFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMyself(ReadOnlyMyself myself) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMyself getMyself() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFoodBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodBookFilePath(Path foodBookFilePath) {
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
        public String listFoods(String mode) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Food> getFilteredFoodList() {
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
        public Height getHeight() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Weight getWeight() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMode(Mode mode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String printMetrics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Mode getMode() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single food.
     */
    private class ModelStubWithFood extends ModelStub {
        private final Food food;

        ModelStubWithFood(Food food) {
            requireNonNull(food);
            this.food = food;
        }

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return this.food.isSameFood(food);
        }
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingFoodAdded extends ModelStub {
        final ArrayList<Food> foodsAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodsAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodsAdded.add(food);
        }

        @Override
        public ReadOnlyFoodBook getFoodBook() {
            return new FoodBook();
        }
    }

}
