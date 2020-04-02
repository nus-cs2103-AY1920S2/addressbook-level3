package seedu.recipe.model;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.plan.ReadOnlyPlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * Represents the in-memory model of the recipe book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RecipeBook recipeBook;
    private final PlannedBook plannedBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Recipe> filteredRecipes;
    private final VersionedRecipeBook states;
    private final CookedRecordBook cookedRecordBook;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<PlannedRecipe> filteredPlannedRecipes;


    /**
     * Initializes a ModelManager with the given recipeBook and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeBook recipeBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyCookedRecordBook cookedRecordBook, ReadOnlyPlannedBook plannedBook) {
        super();
        requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with recipe book: " + recipeBook + " and user prefs " + userPrefs);

        this.recipeBook = new RecipeBook(recipeBook);
        this.plannedBook = new PlannedBook(plannedBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        this.states = new VersionedRecipeBook(recipeBook);
        this.cookedRecordBook = new CookedRecordBook(cookedRecordBook);
        this.filteredRecords = new FilteredList<>(this.cookedRecordBook.getRecordsList());
        filteredPlannedRecipes = new FilteredList<>(this.plannedBook.getPlannedList());
    }

    public ModelManager() {
        this(new RecipeBook(), new UserPrefs(), new CookedRecordBook(), new PlannedBook());

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
    public Path getRecipeBookFilePath() {
        return userPrefs.getRecipeBookFilePath();
    }

    @Override
    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        requireNonNull(recipeBookFilePath);
        userPrefs.setRecipeBookFilePath(recipeBookFilePath);
    }

    //=========== RecipeBook ================================================================================

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBook.resetData(recipeBook);
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeBook.hasRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeBook.removeRecipe(target);
    }

    @Override
    public void favouriteRecipe(Recipe target) {
        recipeBook.favouriteRecipe(target);
    }

    @Override
    public void unfavouriteRecipe(Recipe target) {
        recipeBook.unfavouriteRecipe(target);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);
        recipeBook.setRecipe(target, editedRecipe);
    }

    @Override
    public boolean canUndo(int numberOfUndo) {
        return states.canUndo(numberOfUndo);
    }

    @Override
    public boolean canRedo(int numberOfRedo) {
        return states.canRedo(numberOfRedo);
    }

    @Override
    public void commitRecipeBook() {
        states.commit(new RecipeBook(recipeBook));
    }

    @Override
    public void undoRecipeBook(int numberOfUndo) {
        setRecipeBook(states.undo(numberOfUndo));
    }

    @Override
    public void redoRecipeBook(int numberOfRedo) {
        setRecipeBook(states.redo(numberOfRedo));
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedRecipeBook}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    //=========== PlannedBook ================================================================================

    @Override
    public ReadOnlyPlannedBook getPlannedBook() {
        return plannedBook;
    }

    @Override
    public void setPlannedBook(PlannedBook plannedBook) {
        this.plannedBook.resetData(plannedBook);
    }

    //=========== Plan Recipe List Accessors =============================================================

    @Override
    public void addPlanForOneRecipe(Recipe recipe, PlannedRecipe plannedRecipe) {
        plannedBook.addPlanForOneRecipe(recipe, plannedRecipe);
    }

    @Override
    public void addPlanForAllRecipes(List<Recipe> recipes, PlannedRecipe plannedRecipe) {
        plannedBook.addPlanForAllRecipes(recipes, plannedRecipe);
    }

    @Override
    public void deleteRecipeFromOnePlan(Recipe recipe, PlannedRecipe plannedRecipe) {
        plannedBook.deleteRecipeFromPlannedRecipe(recipe, plannedRecipe);
    }

    @Override
    public void deleteAllPlansFor(Recipe recipe) {
        plannedBook.deleteAllPlansFor(recipe);
    }

    @Override
    public void setRecipeInPlans(Recipe target, Recipe editedRecipe) {
        plannedBook.setRecipeInPlans(target, editedRecipe);
    }

    @Override
    public ObservableList<PlannedRecipe> getFilteredPlannedList() {
        return filteredPlannedRecipes;
    }

    @Override
    public void updateFilteredPlannedList(Predicate<PlannedRecipe> predicate) {
        requireNonNull(predicate);
        filteredPlannedRecipes.setPredicate(predicate);
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
        return recipeBook.equals(other.recipeBook)
                && plannedBook.equals(other.plannedBook)
                && userPrefs.equals(other.userPrefs)
                && filteredRecipes.equals(other.filteredRecipes);
    }



    //=========== Cooked Recipe List Accessors =============================================================

    @Override
    public void addRecord(Record record) {
        cookedRecordBook.addRecord(record);
    }

    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return filteredRecords;
    }

    @Override
    public ObservableList<Integer> getFilteredGoalsTally() {
        return cookedRecordBook.getFilteredGoalsTally();
    }

    @Override
    public ReadOnlyCookedRecordBook getRecordBook() {
        return cookedRecordBook;
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return cookedRecordBook.hasRecord(record);
    }

}
