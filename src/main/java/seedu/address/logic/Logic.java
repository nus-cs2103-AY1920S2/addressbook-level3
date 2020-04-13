package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Recipe;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Cookbook.
     *
     * @see seedu.address.model.Model#getCookbook()
     */
    ReadOnlyCookbook getCookbook();

    /**
     * Returns the Inventory.
     *
     * @see seedu.address.model.Model#getInventory()
     */
    ReadOnlyInventory getInventory();

    /**
     * Returns the Cart.
     *
     * @see seedu.address.model.Model#getCart()
     */
    ReadOnlyCart getCart();

    /** Returns an unmodifiable view of the filtered list of cookbook recipes */
    ObservableList<Recipe> getFilteredCookbookRecipeList();

    /** Returns an unmodifiable view of the filtered list of inventory ingredients */
    ObservableList<Ingredient> getFilteredInventoryIngredientList();

    /** Returns an unmodifiable view of the filtered list of cart ingredients */
    ObservableList<Ingredient> getFilteredCartIngredientList();

    /** Returns an unmodifiable view of the filtered cart */
    ObservableList<Ingredient> getFilteredCart();

    /**
     * Returns the user prefs' cookbook file path.
     */
    Path getCookbookFilePath();

    /**
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Returns the user prefs' cart file path.
     */
    Path getCartFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
