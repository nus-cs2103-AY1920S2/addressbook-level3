package seedu.eylah.diettracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.parser.FoodBookParser;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.storage.DietStorage;

/**
 * The main LogicManager of the app.
 */
public class DietLogicManager implements DietLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(DietLogicManager.class);

    private final DietModel model;
    //private final FoodBookStorage storage;
    private final DietStorage dietStorage;
    private final FoodBookParser foodBookParser;

    public DietLogicManager(DietModel model, DietStorage dietStorage) {
        this.model = model;
        this.dietStorage = dietStorage;
        foodBookParser = new FoodBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        Command<DietModel> command = foodBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            logger.info("Food Book: " + model.getFoodBook().toString());
            logger.info("Myself: " + model.getMyself().toString());
            dietStorage.saveFoodBook(model.getFoodBook());
            dietStorage.saveMyself(model.getMyself());
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
    public ReadOnlyMyself getMyself() {
        return model.getMyself();
    }

    @Override
    public Path getMyselfFilePath() {
        return model.getMyselfFilePath();
    }

}
