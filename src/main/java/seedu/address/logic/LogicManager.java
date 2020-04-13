package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CookingPapaParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Recipe;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CookingPapaParser cookingPapaParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        cookingPapaParser = new CookingPapaParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("USER COMMAND: " + commandText);

        CommandResult commandResult;
        Command command = cookingPapaParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveCookbook(model.getCookbook());
            storage.saveInventory(model.getInventory());
            storage.saveCart(model.getCart());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCookbook getCookbook() {
        return model.getCookbook();
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return model.getInventory();
    }

    @Override
    public ReadOnlyCart getCart() {
        return model.getCart();
    }

    @Override
    public ObservableList<Recipe> getFilteredCookbookRecipeList() {
        return model.getFilteredCookbookRecipeList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredInventoryIngredientList() {
        return model.getFilteredInventoryIngredientList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredCartIngredientList() {
        return model.getFilteredCartIngredientList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredCart() {
        return model.getFilteredCartIngredientList();
    }

    @Override
    public Path getCookbookFilePath() {
        return model.getCookbookFilePath();
    }

    @Override
    public Path getInventoryFilePath() {
        return model.getInventoryFilePath();
    }

    @Override
    public Path getCartFilePath() {
        return model.getCartFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
