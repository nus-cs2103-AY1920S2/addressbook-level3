package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.ALMOND;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.BANANA;
import static seedu.address.testutil.TypicalIngredients.getTypicalInventory;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;
import static seedu.address.testutil.TypicalRecipes.SCRAMBLED_EGG;
import static seedu.address.testutil.TypicalRecipes.SPAGHETTI_BOLOGNESE;
import static seedu.address.testutil.TypicalRecipes.getTypicalCookbook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.recipe.RecipeInventoryIngredientsSimilarityComparator;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Cookbook(), new Cookbook(modelManager.getCookbook()));
        assertEquals(new Inventory(), new Inventory(modelManager.getInventory()));
        assertEquals(new Cart(), new Cart(modelManager.getCart()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCookbookFilePath(Paths.get("cookbook/file/path"));
        userPrefs.setInventoryFilePath(Paths.get("inventory/file/path"));
        userPrefs.setCartFilePath(Paths.get("cart/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCookbookFilePath(Paths.get("new/cookbook/file/path"));
        userPrefs.setInventoryFilePath(Paths.get("new/inventory/file/path"));
        userPrefs.setCartFilePath(Paths.get("new/cart/file/path"));
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
    public void setCookbookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCookbookFilePath(null));
    }

    @Test
    public void setCookbookFilePath_validPath_setsCookbookFilePath() {
        Path path = Paths.get("cookbook/file/path");
        modelManager.setCookbookFilePath(path);
        assertEquals(path, modelManager.getCookbookFilePath());
    }

    @Test
    public void setInventoryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInventoryFilePath(null));
    }

    @Test
    public void setInventoryFilePath_validPath_setsInventoryFilePath() {
        Path path = Paths.get("inventory/file/path");
        modelManager.setInventoryFilePath(path);
        assertEquals(path, modelManager.getInventoryFilePath());
    }

    @Test
    public void hasClearedInventory_ingredientNotInInventory_returnsTrue() {
        modelManager.addInventoryIngredient(APPLE);
        modelManager.addInventoryIngredient(BANANA);
        modelManager.setInventory(modelManager.getInventory());
        assertTrue(!modelManager.hasInventoryIngredient(APPLE)
                && !modelManager.hasInventoryIngredient(BANANA));
    }

    @Test
    public void setCartFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCartFilePath(null));
    }

    @Test
    public void setCartFilePath_validPath_setsCartFilePath() {
        Path path = Paths.get("cart/file/path");
        modelManager.setCartFilePath(path);
        assertEquals(path, modelManager.getCartFilePath());
    }

    @Test
    public void hasCookbookRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCookbookRecipe(null));
    }

    @Test
    public void hasCookbookRecipe_recipeNotInCookbook_returnsFalse() {
        assertFalse(modelManager.hasCookbookRecipe(CARBONARA));
    }

    @Test
    public void hasCookbookRecipe_recipeInCookbook_returnsTrue() {
        modelManager.addCookbookRecipe(CARBONARA);
        assertTrue(modelManager.hasCookbookRecipe(CARBONARA));
    }

    @Test
    public void hasInventoryIngredient_nullIngredient_throwsNullIngredientException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasInventoryIngredient(null));
    }

    @Test
    public void hasInventoryIngredient_ingredientNotInCart_returnsFalse() {
        assertFalse(modelManager.hasInventoryIngredient(APPLE));
    }

    @Test
    public void hasInventoryIngredient_ingredientInCart_returnsTrue() {
        modelManager.addInventoryIngredient(APPLE);
        assertTrue(modelManager.hasInventoryIngredient(APPLE));
    }

    @Test
    public void hasCartIngredient_nullIngredient_throwsNullIngredientException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCartIngredient(null));
    }

    @Test
    public void hasCartIngredient_ingredientNotInCart_returnsFalse() {
        assertFalse(modelManager.hasCartIngredient(APPLE));
    }

    @Test
    public void hasCartIngredient_ingredientInCart_returnsTrue() {
        modelManager.addCartIngredient(APPLE);
        assertTrue(modelManager.hasCartIngredient(APPLE));
    }

    @Test
    public void hasClearedCart_ingredientNotInCart_returnsTrue() {
        modelManager.addCartIngredient(APPLE);
        modelManager.addCartIngredient(ALMOND);
        modelManager.setCart(modelManager.getCart());
        assertTrue(!modelManager.hasCartIngredient(APPLE) && !modelManager.hasCartIngredient(ALMOND));
    }

    @Test
    public void sortCookbook() {
        modelManager.setCookbook(getTypicalCookbook());
        modelManager.sortCookbook(new RecipeInventoryIngredientsSimilarityComparator(getTypicalInventory()));

        assertEquals(SCRAMBLED_EGG, modelManager.getCookbook().getRecipeList().get(0));
        assertEquals(CARBONARA, modelManager.getCookbook().getRecipeList().get(1));
        assertEquals(SPAGHETTI_BOLOGNESE, modelManager.getCookbook().getRecipeList().get(2));
        assertEquals(AGLIO_OLIO, modelManager.getCookbook().getRecipeList().get(3));
    }

    @Test
    public void getFilteredCookbookRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
                .getFilteredCookbookRecipeList().remove(0));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(new Cookbook(), new Inventory(), new Cart(), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(new Cookbook(), new Inventory(), new Cart(),
                userPrefs);

        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCookbookFilePath(Paths.get("differentCookbookFilePath"));
        differentUserPrefs.setInventoryFilePath(Paths.get("differentInventoryFilePath"));
        differentUserPrefs.setCartFilePath(Paths.get("differentCartFilePath"));
        assertFalse(modelManager.equals(new ModelManager(new Cookbook(), new Inventory(), new Cart(),
                differentUserPrefs)));
    }
}
