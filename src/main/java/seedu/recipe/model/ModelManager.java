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
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.plan.PlannedDate;
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
    private final MultipleBookStateManager states;
    private final CookedRecordBook cookedRecordBook;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<PlannedDate> filteredPlannedDates;


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
        this.cookedRecordBook = new CookedRecordBook(cookedRecordBook);
        this.filteredRecords = new FilteredList<>(this.cookedRecordBook.getRecordsList());
        this.states = new MultipleBookStateManager(recipeBook, plannedBook, cookedRecordBook);
        filteredPlannedDates = new FilteredList<>(this.plannedBook.getPlannedList());
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
        plannedBook.deleteAllRecipePlans(target);
    }

    @Override
    public void favouriteRecipe(Recipe target) {
        recipeBook.favouriteRecipe(target);
        plannedBook.favouriteRecipe(target);
    }

    @Override
    public void unfavouriteRecipe(Recipe target) {
        recipeBook.unfavouriteRecipe(target);
        plannedBook.unfavouriteRecipe(target);
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
        plannedBook.setRecipe(target, editedRecipe);
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
    public void commitBook(CommandType commandType) {
        switch (commandType) {
        case MAIN_LONE:
            states.commitRecipeBook(new RecipeBook(recipeBook), commandType);
            break;
        case MAIN:
            states.commitRecipeAndPlannedBook(new RecipeBook(recipeBook), new PlannedBook(plannedBook), commandType);
            break;
        case PLAN:
            states.commitPlannedBook(new PlannedBook(plannedBook), commandType);
            break;
        case GOALS:
            // Need to develop methods for deleting records before this can be implemented
            break;
        default:
            // This block will never be reached
            break;
        }
    }

    @Override
    public void undoBook(int numberOfUndo, Model model) {
        states.undo(numberOfUndo, model);
    }

    @Override
    public void redoBook(int numberOfRedo, Model model) {
        states.redo(numberOfRedo, model);
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code multipleBookManager}
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
    public void setPlannedBook(ReadOnlyPlannedBook plannedBook) {
        this.plannedBook.resetData(plannedBook);
    }

    //=========== Plan Recipe List Accessors =============================================================

    @Override
    public void addOnePlan(Recipe recipe, PlannedDate plannedDate) {
        plannedBook.addOnePlan(recipe, plannedDate);
    }

    @Override
    public void addAllRecipesToPlan(List<Recipe> recipes, PlannedDate plannedDate) {
        plannedBook.addAllRecipesToPlan(recipes, plannedDate);
    }

    @Override
    public void deleteOnePlan(Recipe recipe, PlannedDate plannedDate) {
        plannedBook.deleteOnePlan(recipe, plannedDate);
    }

    @Override
    public void deleteAllRecipePlans(Recipe recipe) {
        plannedBook.deleteAllRecipePlans(recipe);
    }

    @Override
    public void setRecipeInPlans(Recipe target, Recipe editedRecipe) {
        plannedBook.setRecipe(target, editedRecipe);
    }

    @Override
    public ObservableList<PlannedDate> getFilteredPlannedList() {
        return filteredPlannedDates;
    }

    @Override
    public void updateFilteredPlannedList(Predicate<PlannedDate> predicate) {
        requireNonNull(predicate);
        filteredPlannedDates.setPredicate(predicate);
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
    public ReadOnlyCookedRecordBook getRecordBook() {
        return cookedRecordBook;
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return cookedRecordBook.hasRecord(record);
    }

}
