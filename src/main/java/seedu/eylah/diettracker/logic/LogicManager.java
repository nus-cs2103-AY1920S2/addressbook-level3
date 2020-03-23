package seedu.eylah.diettracker.logic;

// import java.io.IOException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.eylah.commons.core.GuiSettings;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.diettracker.logic.commands.Command;
import seedu.eylah.diettracker.logic.commands.CommandResult;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.logic.parser.FoodBookParser;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.storage.FoodBookStorage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final FoodBookStorage storage;
    private final FoodBookParser foodBookParser;

    public LogicManager(Model model, FoodBookStorage storage) {
        this.model = model;
        this.storage = storage;
        foodBookParser = new FoodBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        Command command = foodBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFoodBook(model.getFoodBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        } catch (Exception e) {
            logger.info(e.toString());
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFoodBook getFoodBook() {
        return model.getFoodBook();
    }

    @Override
    public ObservableList<Food> getFilteredFoodList() {
        return model.getFilteredFoodList();
    }

    @Override
    public Path getFoodBookFilePath() {
        return model.getFoodBookFilePath();
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
