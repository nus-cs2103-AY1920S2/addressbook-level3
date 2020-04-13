package seedu.recipe.model;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.GoalCount;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.plan.PlannedRecipeMap;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.ui.tab.Tab;

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
    private final FilteredList<Plan> filteredPlannedDates;
    private final QuoteBook quoteBook;
    private final FilteredList<Quote> filteredQuotes;


    /**
     * Initializes a ModelManager with the given recipeBook and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeBook recipeBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyCookedRecordBook cookedRecordBook,
                        ReadOnlyPlannedBook plannedBook, ReadOnlyQuoteBook quoteBook) {
        super();
        requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with recipe book: " + recipeBook + " and user prefs " + userPrefs);

        this.recipeBook = new RecipeBook(recipeBook);
        this.plannedBook = new PlannedBook(plannedBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        this.cookedRecordBook = new CookedRecordBook(cookedRecordBook);
        this.quoteBook = new QuoteBook(quoteBook);
        this.filteredRecords = new FilteredList<>(this.cookedRecordBook.getRecordsList());
        this.states = new MultipleBookStateManager(recipeBook, plannedBook, cookedRecordBook, quoteBook);
        filteredPlannedDates = new FilteredList<>(this.plannedBook.getPlannedList());
        filteredQuotes = new FilteredList<>(this.quoteBook.getQuoteList());
    }

    public ModelManager() {
        this(new RecipeBook(), new UserPrefs(), new CookedRecordBook(), new PlannedBook(), new QuoteBook());

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
        plannedBook.deleteRecipe(target);
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
    public void commitBook(CommandType commandType, Tab tab) {
        switch (commandType) {
        case MAIN_LONE:
            states.commitRecipeBook(new RecipeBook(recipeBook), commandType, tab);
            break;
        case MAIN:
            states.commitRecipeAndPlannedBook(
                    new RecipeBook(recipeBook), new PlannedBook(plannedBook), commandType, tab);
            break;
        case PLAN:
            states.commitPlannedBook(new PlannedBook(plannedBook), commandType, tab);
            break;
        case GOALS:
            states.commitCookedRecordBook(new CookedRecordBook(cookedRecordBook), commandType, tab);
            break;
        case QUOTE:
            states.commitQuoteBook(new QuoteBook(quoteBook), commandType, tab);
            break;
        default:
            // This block will never be reached
            break;
        }
    }

    @Override
    public Tab undoBook(int numberOfUndo, Model model) {
        return states.undo(numberOfUndo, model);
    }

    @Override
    public Tab redoBook(int numberOfRedo, Model model) {
        return states.redo(numberOfRedo, model);
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
    public void addPlan(Recipe recipe, Plan plan) {
        requireAllNonNull(recipe, plan);
        plannedBook.addPlan(recipe, plan);
    }

    @Override
    public void deletePlan(Recipe recipe, Plan plan) {
        requireAllNonNull(recipe, plan);
        plannedBook.deletePlan(recipe, plan);
    }

    @Override
    public Optional<List<Plan>> getPlans(Recipe recipe) {
        requireNonNull(recipe);
        return plannedBook.getPlans(recipe);
    }

    @Override
    public String getGroceryList() {
        return plannedBook.getGroceryList();
    }

    @Override
    public void setGroceryList(String groceryList) {
        plannedBook.setGroceryList(groceryList);
    }

    @Override
    public ObservableList<Plan> getFilteredPlannedList() {
        return filteredPlannedDates;
    }

    @Override
    public void updateFilteredPlannedList(Predicate<Plan> predicate) {
        requireNonNull(predicate);
        filteredPlannedDates.setPredicate(predicate);
    }

    @Override
    public PlannedRecipeMap getPlannedMap() {
        return plannedBook.getPlannedMap();
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
    public void updateGoalsTally(Record record) {
        cookedRecordBook.updateGoalsTally(record);
    }

    @Override
    public ObservableList<GoalCount> getFilteredGoalsTally() {
        return cookedRecordBook.getFilteredGoalsTally();
    }

    @Override
    public ReadOnlyCookedRecordBook getRecordBook() {
        return cookedRecordBook;
    }

    @Override
    public void setCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook) {
        this.cookedRecordBook.resetData(cookedRecordBook);
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return cookedRecordBook.hasRecord(record);
    }

    //=========== Quote List Accessors =============================================================

    @Override
    public ObservableList<Quote> getFilteredQuoteList() {
        return filteredQuotes;
    }

    @Override
    public void setQuoteBook(ReadOnlyQuoteBook quoteBook) {
        this.quoteBook.resetData(quoteBook);
    }

    @Override
    public ReadOnlyQuoteBook getQuoteBook() {
        return quoteBook;
    }

    @Override
    public boolean hasQuote (Quote quote) {
        requireNonNull(quote);
        return quoteBook.hasQuote(quote);
    }

    @Override
    public void addQuote(Quote quote) {
        quoteBook.addQuote(quote);
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
}
