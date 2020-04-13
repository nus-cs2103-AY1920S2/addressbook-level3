package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.recipe.Recipe;

/**
 * Represents the in-memory model of CookingPapa's data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Cookbook cookbook;
    private final Inventory inventory;
    private final Cart cart;
    private final UserPrefs userPrefs;
    private final FilteredList<Recipe> filteredCookbookRecipes;
    private final FilteredList<Ingredient> filteredInventoryIngredients;
    private final FilteredList<Ingredient> filteredCartIngredients;

    /**
     * Initializes a ModelManager with the given cookbook, inventory, cart, and userPrefs.
     */
    public ModelManager(ReadOnlyCookbook cookbook, ReadOnlyInventory inventory,
            ReadOnlyCart cart, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cookbook, inventory, cart, userPrefs);

        logger.fine("Initializing with cookbook: " + cookbook + ", inventory: " + inventory + ", cart: " + cart
                + ", and user prefs: " + userPrefs);

        this.cookbook = new Cookbook(cookbook);
        this.inventory = new Inventory(inventory);
        this.cart = new Cart(cart);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCookbookRecipes = new FilteredList<>(this.cookbook.getRecipeList());
        filteredInventoryIngredients = new FilteredList<>(this.inventory.getIngredientList());
        filteredCartIngredients = new FilteredList<>(this.cart.getIngredientList());
    }

    public ModelManager() {
        this(new Cookbook(), new Inventory(), new Cart(), new UserPrefs());
    }


    // User prefs methods
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

    // File path methods
    @Override
    public Path getCookbookFilePath() {
        return userPrefs.getCookbookFilePath();
    }

    @Override
    public void setCookbookFilePath(Path cookbookFilePath) {
        requireNonNull(cookbookFilePath);
        userPrefs.setCookbookFilePath(cookbookFilePath);
    }

    @Override
    public Path getInventoryFilePath() {
        return userPrefs.getInventoryFilePath();
    }

    @Override
    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        userPrefs.setInventoryFilePath(inventoryFilePath);
    }

    @Override
    public Path getCartFilePath() {
        return userPrefs.getCartFilePath();
    }

    @Override
    public void setCartFilePath(Path cartFilePath) {
        requireNonNull(cartFilePath);
        userPrefs.setCartFilePath(cartFilePath);
    }

    // Cookbook methods
    @Override
    public void setCookbook(ReadOnlyCookbook cookbook) {
        this.cookbook.resetData(cookbook);
    }

    @Override
    public ReadOnlyCookbook getCookbook() {
        return cookbook;
    }

    @Override
    public boolean hasCookbookRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return cookbook.hasRecipe(recipe);
    }

    @Override
    public void removeCookbookRecipe(Recipe target) {
        cookbook.removeRecipe(target);
    }

    @Override
    public void addCookbookRecipe(Recipe recipe) {
        cookbook.addRecipe(recipe);
        updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setCookbookRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);
        cookbook.setRecipe(target, editedRecipe);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedCookbook}
     */
    @Override
    public ObservableList<Recipe> getFilteredCookbookRecipeList() {
        return filteredCookbookRecipes;
    }

    @Override
    public void updateFilteredCookbookRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredCookbookRecipes.setPredicate(predicate);
    }

    // Inventory methods
    @Override
    public void setInventory(ReadOnlyInventory inventory) {
        this.inventory.resetData(inventory);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean hasInventoryIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return inventory.hasIngredient(ingredient);
    }

    @Override
    public void removeInventoryIngredient(Ingredient target) {
        inventory.removeIngredient(target);
    }

    @Override
    public void removeInventoryIngredient(IngredientName target) {
        inventory.removeIngredient(target);
    }

    @Override
    public void addInventoryIngredient(Ingredient ingredient) {
        inventory.addIngredient(ingredient);
        updateFilteredInventoryIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void setInventoryIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);
        inventory.setIngredient(target, editedIngredient);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Ingredient} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Ingredient> getFilteredInventoryIngredientList() {
        return filteredInventoryIngredients;
    }

    @Override
    public void updateFilteredInventoryIngredientList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredInventoryIngredients.setPredicate(predicate);
    }

    // Cart methods
    @Override
    public void setCart(ReadOnlyCart cart) {
        this.cart.resetData(cart);
    }

    @Override
    public ReadOnlyCart getCart() {
        return cart;
    }

    @Override
    public boolean hasCartIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return cart.hasIngredient(ingredient);
    }

    @Override
    public void removeCartIngredient(Ingredient target) {
        cart.removeIngredient(target);
    }

    @Override
    public void removeCartIngredient(IngredientName target) {
        cart.removeIngredient(target);
    }

    @Override
    public void addCartIngredient(Ingredient ingredient) {
        cart.addIngredient(ingredient);
        updateFilteredCartIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void setCartIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);
        cart.setIngredient(target, editedIngredient);
    }

    @Override
    public void sortCookbook(Comparator<? super Recipe> comparator) {
        this.cookbook.sort(comparator);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Ingredient} backed by the internal list of
     * {@code versionedCart}
     */
    @Override
    public ObservableList<Ingredient> getFilteredCartIngredientList() {
        return filteredCartIngredients;
    }

    @Override
    public void updateFilteredCartIngredientList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredCartIngredients.setPredicate(predicate);
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
        return cookbook.equals(other.cookbook)
            && userPrefs.equals(other.userPrefs)
            && inventory.equals(other.inventory)
            && cart.equals(other.cart)
            && filteredCartIngredients.equals(other.filteredCartIngredients)
            && filteredCookbookRecipes.equals(other.filteredCookbookRecipes)
            && filteredInventoryIngredients.equals(other.filteredInventoryIngredients);
    }
}
