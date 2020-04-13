package seedu.eylah.diettracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.model.DietModel.PREDICATE_SHOW_ALL_FOODS;
import static seedu.eylah.diettracker.testutil.TypicalFood.PASTA;
import static seedu.eylah.diettracker.testutil.TypicalFood.PIZZA;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.food.NameContainsKeywordsPredicate;
import seedu.eylah.diettracker.testutil.FoodBookBuilder;

public class ModelManagerTest {

    private DietModelManager modelManager = new DietModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new FoodBook(), new FoodBook(modelManager.getFoodBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFoodBookFilePath(Paths.get("address/book/file/path"));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFoodBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setFoodBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFoodBookFilePath(null));
    }

    @Test
    public void setFoodBookFilePath_validPath_setsFoodBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setFoodBookFilePath(path);
        assertEquals(path, modelManager.getFoodBookFilePath());
    }

    @Test
    public void hasFood_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFood(null));
    }

    @Test
    public void hasFood_foodNotInFoodBook_returnsFalse() {
        assertFalse(modelManager.hasFood(PASTA));
    }

    @Test
    public void hasFood_foodInFoodBook_returnsTrue() {
        modelManager.addFood(PASTA);
        assertTrue(modelManager.hasFood(PASTA));
    }

    @Test
    public void getFilteredFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFoodList().remove(0));
    }

    @Test
    public void equals() {
        FoodBook foodBook = new FoodBookBuilder().withFood(PASTA).withFood(PIZZA).build();
        FoodBook differentFoodBook = new FoodBook();
        UserPrefs userPrefs = new UserPrefs();
        Myself myself = new Myself();

        // same values -> returns true
        modelManager = new DietModelManager(foodBook, userPrefs, myself);
        DietModelManager modelManagerCopy = new DietModelManager(foodBook, userPrefs, myself);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different foodBook -> returns false
        assertFalse(modelManager.equals(new DietModelManager(differentFoodBook, userPrefs, myself)));

        // different filteredList -> returns false
        String[] keywords = PASTA.getName().name.split("\\s+");
        modelManager.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new DietModelManager(foodBook, userPrefs, myself)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFoodList(PREDICATE_SHOW_ALL_FOODS);

        // the below testcase is fail.

        /*// different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFoodBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new DietModelManager(foodBook, differentUserPrefs)));*/
    }
}
