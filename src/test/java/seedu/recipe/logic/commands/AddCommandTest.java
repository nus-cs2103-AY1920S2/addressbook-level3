package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.commands.recipe.AddCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.GoalCount;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedRecipeMap;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

public class AddCommandTest {

    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_recipeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecipeAdded modelStub = new ModelStubAcceptingRecipeAdded();
        Recipe validRecipe = new RecipeBuilder().build();

        CommandResult commandResult = new AddCommand(validRecipe).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRecipe), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecipe), modelStub.recipesAdded);
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() {
        Recipe validRecipe = new RecipeBuilder().build();
        AddCommand addCommand = new AddCommand(validRecipe);
        ModelStub modelStub = new ModelStubWithRecipe(validRecipe);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_RECIPE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Recipe salad = new RecipeBuilder().withName("Salad").build();
        Recipe bread = new RecipeBuilder().withName("Bread").build();
        AddCommand addSaladCommand = new AddCommand(salad);
        AddCommand addBreadCommand = new AddCommand(bread);

        // same object -> returns true
        assertTrue(addSaladCommand.equals(addSaladCommand));

        // same values -> returns true
        AddCommand addSaladCommandCopy = new AddCommand(salad);
        assertTrue(addSaladCommand.equals(addSaladCommandCopy));

        // different types -> returns false
        assertFalse(addSaladCommand.equals(1));

        // null -> returns false
        assertFalse(addSaladCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addSaladCommand.equals(addBreadCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getRecipeBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecipeBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecipe(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecipeBook(ReadOnlyRecipeBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecipe(Recipe target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecipe(Recipe target, Recipe editedRecipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo(int numberOfUndo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo(int numberOfRedo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitBook(CommandType commandType, Tab tab) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tab undoBook(int numberOfUndo, Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tab redoBook(int numberOfRedo, Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPlannedBook getPlannedBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPlannedBook(ReadOnlyPlannedBook plannedBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPlan(Recipe recipe, Plan plan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePlan(Recipe recipe, Plan plan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Plan> getFilteredPlannedList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateGoalsTally(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GoalCount> getFilteredGoalsTally() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCookedRecordBook getRecordBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPlannedList(Predicate<Plan> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PlannedRecipeMap getPlannedMap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<List<Plan>> getPlans(Recipe recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryList(String groceryList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasQuote(Quote quote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Quote> getFilteredQuoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuoteBook(ReadOnlyQuoteBook quoteBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyQuoteBook getQuoteBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuote(Quote quote) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single recipe.
     */
    private class ModelStubWithRecipe extends ModelStub {
        private final Recipe recipe;

        ModelStubWithRecipe(Recipe recipe) {
            requireNonNull(recipe);
            this.recipe = recipe;
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return this.recipe.isSameRecipe(recipe);
        }
    }

    /**
     * A Model stub that always accept the recipe being added.
     */
    private class ModelStubAcceptingRecipeAdded extends ModelStub {
        final ArrayList<Recipe> recipesAdded = new ArrayList<>();

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return recipesAdded.stream().anyMatch(recipe::isSameRecipe);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            requireNonNull(recipe);
            recipesAdded.add(recipe);
        }

        @Override
        public void commitBook(CommandType commandType, Tab tab) {
            requireNonNull(commandType);
            requireNonNull(tab);
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            return new RecipeBook();
        }
    }

    /**
     * A Model stub that contains a single record.
     */
    private class ModelStubWithRecord extends ModelStub {
        private final Record record;

        ModelStubWithRecord(Record record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(Record record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public ReadOnlyCookedRecordBook getRecordBook() {
            return new CookedRecordBook();
        }
    }
}
