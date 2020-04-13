package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.recipe.Recipe;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' cookbook file path.
     */
    Path getCookbookFilePath();

    /**
     * Sets the user prefs' cookbook file path.
     */
    void setCookbookFilePath(Path cookbookFilePath);

    /**
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Sets the user prefs' inventory file path.
     */
    void setInventoryFilePath(Path inventoryFilePath);

    /**
     * Returns the user prefs' cart file path.
     */
    Path getCartFilePath();

    /**
     * Sets the user prefs' cart file path.
     */
    void setCartFilePath(Path cartFilePath);

    /**
     * Replaces cookbook data with the data in {@code cookbook}.
     */
    void setCookbook(ReadOnlyCookbook cookbook);

    /** Returns the Cookbook */
    ReadOnlyCookbook getCookbook();

    /**
     * Replaces inventory data with the data in {@code inventory}.
     */
    void setInventory(ReadOnlyInventory inventory);

    /** Returns the Inventory */
    ReadOnlyInventory getInventory();

    /**
     * Replaces cart data with the data in {@code cart}.
     */
    void setCart(ReadOnlyCart cart);

    /** Returns the Cart */
    ReadOnlyCart getCart();

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the cookbook.
     */
    boolean hasCookbookRecipe(Recipe recipe);

    /**
     * Removes the given recipe.
     * The recipe must exist in the cookbook.
     */
    void removeCookbookRecipe(Recipe recipe);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in the cookbook.
     */
    void addCookbookRecipe(Recipe recipe);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in the cookbook.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the cookbook.
     */
    void setCookbookRecipe(Recipe target, Recipe editedRecipe);

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the inventory.
     */
    boolean hasInventoryIngredient(Ingredient ingredient);

    /**
     * Removes the given ingredient.
     * The ingredient must exist in the inventory.
     */
    void removeInventoryIngredient(Ingredient ingredient);

    /**
     * Removes ingredients with the given ingredient name.
     * The ingredient must exist in the inventory.
     */
    void removeInventoryIngredient(IngredientName ingredientName);

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the inventory.
     */
    void addInventoryIngredient(Ingredient ingredient);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}.
     * {@code target} must exist in the inventory.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the
     * inventory.
     */
    void setInventoryIngredient(Ingredient target, Ingredient editedIngredient);

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the cart.
     */
    boolean hasCartIngredient(Ingredient ingredient);

    /**
     * Removes the given ingredient.
     * The ingredient must exist in the cart.
     */
    void removeCartIngredient(Ingredient ingredient);

    /**
     * Removes ingredients with the given ingredient name.
     * The ingredient must exist in the cart.
     */
    void removeCartIngredient(IngredientName ingredientName);

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the cart.
     */
    void addCartIngredient(Ingredient ingredient);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}.
     * {@code target} must exist in the cart.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the
     * cart.
     */
    void setCartIngredient(Ingredient target, Ingredient editedIngredient);

    /**
     * Sorts the cookbook using the specified comparator.
     */
    void sortCookbook(Comparator<? super Recipe> comparator);

    /** Returns an unmodifiable view of the filtered cookbook recipe list */
    ObservableList<Recipe> getFilteredCookbookRecipeList();

    /**
     * Updates the filter of the filtered cookbook recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCookbookRecipeList(Predicate<Recipe> predicate);

    /** Returns an unmodifiable view of the filtered inventory ingredient list */
    ObservableList<Ingredient> getFilteredInventoryIngredientList();

    /**
     * Updates the filter of the filtered inventory ingredient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInventoryIngredientList(Predicate<Ingredient> predicate);

    /** Returns an unmodifiable view of the filtered cart ingredient list */
    ObservableList<Ingredient> getFilteredCartIngredientList();

    /**
     * Updates the filter of the filtered cart ingredient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCartIngredientList(Predicate<Ingredient> predicate);

}
