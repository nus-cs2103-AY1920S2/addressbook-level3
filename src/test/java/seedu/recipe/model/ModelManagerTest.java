package seedu.recipe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalRecipes.CAESAR_SALAD;
import static seedu.recipe.testutil.TypicalRecipes.GRILLED_SANDWICH;
import static seedu.recipe.testutil.TypicalRecords.BOILED_CHICKEN;
import static seedu.recipe.testutil.TypicalRecords.CHOCOLATE_CAKE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;
import seedu.recipe.testutil.RecipeBookBuilder;
import seedu.recipe.testutil.RecordBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setRecipeBookFilePath(Paths.get("recipe/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setRecipeBookFilePath(Paths.get("new/recipe/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setRecipeBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setRecipeBookFilePath(null));
    }

    @Test
    public void setRecipeBookFilePath_validPath_setsRecipeBookFilePath() {
        Path path = Paths.get("recipe/book/file/path");
        modelManager.setRecipeBookFilePath(path);
        assertEquals(path, modelManager.getRecipeBookFilePath());
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRecipe(null));
    }

    @Test
    public void hasRecipe_recipeNotInRecipeBook_returnsFalse() {
        assertFalse(modelManager.hasRecipe(CAESAR_SALAD));
    }

    @Test
    public void hasRecipe_recipeInRecipeBook_returnsTrue() {
        modelManager.addRecipe(CAESAR_SALAD);
        assertTrue(modelManager.hasRecipe(CAESAR_SALAD));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void equals() {
        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(CAESAR_SALAD).withRecipe(GRILLED_SANDWICH).build();
        RecipeBook differentRecipeBook = new RecipeBook();
        CookedRecordBook recordBook = new RecordBookBuilder().withRecord(BOILED_CHICKEN)
                .withRecord(CHOCOLATE_CAKE).build();
        CookedRecordBook differentRecordBook = new CookedRecordBook();
        UserPrefs userPrefs = new UserPrefs();
        PlannedBook plannedBook = new PlannedBook();

        // same values -> returns true
        modelManager = new ModelManager(recipeBook, userPrefs, recordBook, plannedBook);
        ModelManager modelManagerCopy = new ModelManager(recipeBook, userPrefs, recordBook, plannedBook);

        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different recipeBook -> returns false

        assertFalse(modelManager.equals(new ModelManager(differentRecipeBook, userPrefs,
                differentRecordBook, plannedBook)));

        // different filteredList -> returns false
        String[] keywords = CAESAR_SALAD.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        assertFalse(modelManager.equals(new ModelManager(recipeBook, userPrefs, recordBook, plannedBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRecipeBookFilePath(Paths.get("differentFilePath"));

        assertFalse(modelManager.equals(new ModelManager(recipeBook, differentUserPrefs, recordBook, plannedBook)));

    }
}
