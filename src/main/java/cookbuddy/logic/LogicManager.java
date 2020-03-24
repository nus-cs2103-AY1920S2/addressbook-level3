package cookbuddy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import cookbuddy.commons.core.GuiSettings;
import cookbuddy.commons.core.LogsCenter;
import cookbuddy.logic.commands.Command;
import cookbuddy.logic.commands.CommandResult;
import cookbuddy.logic.commands.exceptions.CommandException;
import cookbuddy.logic.parser.RecipeBookParser;
import cookbuddy.logic.parser.exceptions.ParseException;
import cookbuddy.model.Model;
import cookbuddy.model.ReadOnlyRecipeBook;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final RecipeBookParser recipeBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        recipeBookParser = new RecipeBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = recipeBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveRecipeBook(model.getRecipeBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return model.getRecipeBook();
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return model.getFilteredRecipeList();
    }

    @Override
    public Path getRecipeBookFilePath() {
        return model.getRecipeBookFilePath();
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
