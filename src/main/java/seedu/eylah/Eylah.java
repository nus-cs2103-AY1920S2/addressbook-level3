package seedu.eylah;

import static seedu.eylah.commons.core.Messages.MESSAGE_INITIATION_FAILED;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.Config;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.core.Version;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.exceptions.InitialiseException;
import seedu.eylah.commons.logic.Logic;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.commons.model.Mode;
import seedu.eylah.commons.model.Model;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.JsonUserPrefsStorage;
import seedu.eylah.commons.storage.Storage;
import seedu.eylah.commons.storage.UserPrefsStorage;
import seedu.eylah.commons.util.ConfigUtil;
import seedu.eylah.commons.util.StringUtil;
import seedu.eylah.diettracker.logic.DietLogicManager;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.util.SampleDataUtil;
import seedu.eylah.diettracker.storage.DietStorage;
import seedu.eylah.diettracker.storage.DietStorageManager;
import seedu.eylah.diettracker.storage.FoodBookStorage;
import seedu.eylah.diettracker.storage.JsonFoodBookStorage;
import seedu.eylah.diettracker.storage.JsonMyselfStorage;
import seedu.eylah.diettracker.storage.MyselfStorage;
import seedu.eylah.expensesplitter.logic.SplitterLogicManager;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;
import seedu.eylah.expensesplitter.storage.JsonPersonAmountBookStorage;
import seedu.eylah.expensesplitter.storage.JsonReceiptBookStorage;
import seedu.eylah.expensesplitter.storage.PersonAmountStorage;
import seedu.eylah.expensesplitter.storage.ReceiptStorage;
import seedu.eylah.expensesplitter.storage.SplitterStorage;
import seedu.eylah.expensesplitter.storage.SplitterStorageManager;
import seedu.eylah.ui.Ui;
import seedu.eylah.ui.UiManager;


/**
 * The main entry for the EYLAH.
 */
public class Eylah {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(Eylah.class);

    private Ui ui;
    private UserPrefs userPrefs;
    private UserPrefsStorage userPrefsStorage;
    private Storage storage;
    private Model model;
    private Logic logic;
    private boolean isExit;
    private boolean isBack;
    private String commandWord;

    /**
     * Runs the application until termination.
     */
    public void run() {
        start();
        runCommandLoopUntilExitCommand();
        exit();
    }

    /**
     * Setup the required objects and show welcome message.
     */
    private void start() {
        logger.info("Starting EYLAH " + Eylah.VERSION);
        isExit = false;
        ui = new UiManager();
        Config config = initConfig();
        LogsCenter.init(config);
        userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
    }

    /**
     * Main menu of the EYLAH.
     * Reads the user command and enter different mode based on input, until the user enter the exit command.
     */
    private void runCommandLoopUntilExitCommand() {
        ui.showLogo();
        while (!isExit) {
            ui.showWelcome();
            commandWord = ui.readCommand();
            isBack = false;

            switch(commandWord.toLowerCase()) {
            case "diet": // Fallthrough
            case "1":
                initSetup(Mode.DIET);
                runCommandLoopUntilBackCommand(Mode.DIET);
                break;
            case "split": // Fallthrough
            case "2":
                initSetup(Mode.SPLITTER);
                runCommandLoopUntilBackCommand(Mode.SPLITTER);
                break;
            case "exit":
                isExit = true;
                break;
            case "help":
                ui.showMainHelp();
                break;
            default:
                ui.showError(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

    /**
     * Reads the user command and executes it, until the user enter the exit or back command.
     */
    private void runCommandLoopUntilBackCommand(Mode mode) {
        while (!isExit) {
            ui.showMode(mode);
            commandWord = ui.readCommand();

            try {
                CommandResult commandResult = logic.execute(commandWord);
                isBack = commandResult.isBack();
                isExit = commandResult.isExit();
                ui.showResult(commandResult);
            } catch (CommandException | ParseException e) {
                ui.showError(e.getMessage());
            }
            if (isBack) {
                break;
            }
        }
    }

    /**
     * Initialise all the required objects.
     * @param mode mode the EYLAH
     */
    private void initSetup(Mode mode) {
        try {
            storage = initStorageManager(mode);
            model = initModelManager(storage, mode);
            logic = initLogicManager(mode);
        } catch (InitialiseException e) {
            ui.showError(e.getMessage());
            isExit = true;
        }
    }

    /**
     * Shows exit message and exits.
     */
    private void exit() {
        ui.showExit();
        System.exit(0);
    }

    /**
     * Starting point for whole application.
     */
    public static void main(String[] args) {
        try {
            Eylah eylah = new Eylah();
            eylah.run();
        } catch (Exception e) {
            logger.warning(e.toString());
        }
    }

    /**
     * Returns a {@code StorageManager} with the given path in the {@code userPref}.
     *
     * @param mode current mode of EYLAH
     * @return the storage manager
     */
    private Storage initStorageManager(Mode mode) throws InitialiseException {
        switch(mode) {
        case DIET:
            FoodBookStorage foodBookStorage = new JsonFoodBookStorage(userPrefs.getFoodBookFilePath());
            MyselfStorage myselfStorage = new JsonMyselfStorage(userPrefs.getMyselfFilePath());
            return new DietStorageManager(foodBookStorage, userPrefsStorage, myselfStorage);
        case SPLITTER:
            PersonAmountStorage personAmountStorage =
                    new JsonPersonAmountBookStorage(userPrefs.getPersonAmountBookFilePath());
            ReceiptStorage receiptStorage = new JsonReceiptBookStorage(userPrefs.getReceiptBookFilePath());
            return new SplitterStorageManager(personAmountStorage, receiptStorage, userPrefsStorage);
        default:
            throw new InitialiseException(MESSAGE_INITIATION_FAILED);
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s EYLAH and {@code userPrefs}. <br>
     * The data from the sample will be used instead if {@code storage} is not found,
     * or an empty data will be used instead if errors occur when reading {@code storage}.
     */
    private Model initModelManager(Storage storage, Mode mode) throws InitialiseException {
        switch(mode) {
        case DIET:
            DietStorage dietStorage = (DietStorage) storage;
            Optional<ReadOnlyFoodBook> foodBookOptional;
            ReadOnlyFoodBook initialFoodBookData;
            Optional<ReadOnlyMyself> myselfOptional;
            ReadOnlyMyself initialMyselfData;
            try {
                foodBookOptional = dietStorage.readFoodBook();
                if (foodBookOptional.isEmpty()) {
                    logger.info("Data file not found. Will be starting with a sample FoodBook");
                }
                initialFoodBookData =
                        foodBookOptional.orElseGet(SampleDataUtil::getEmptyFoodBook);
            } catch (DataConversionException e) {
                logger.warning("Data file not in the correct format. Will be starting with an empty FoodBook");
                initialFoodBookData = new FoodBook();
            } catch (IOException e) {
                logger.warning("Problem while reading from the file. Will be starting with an empty FoodBook");
                initialFoodBookData = new FoodBook();
            }

            try {
                myselfOptional = dietStorage.readMyself();
                if (myselfOptional.isEmpty()) {
                    logger.info("Data file not found. Will be starting with a sample Myself (User health metrics)");
                }
                initialMyselfData = myselfOptional.orElseGet(SampleDataUtil::getSampleMyself);
            } catch (DataConversionException e) {
                logger.warning("Data file not in the correct format. Will be starting with an empty Myself");
                initialMyselfData = new Myself();
            } catch (IOException e) {
                logger.warning("Problem while reading from the file. Will be starting with an empty Myself");
                initialMyselfData = new Myself();
            }

            return new DietModelManager(initialFoodBookData, userPrefs, initialMyselfData);

        case SPLITTER:
            SplitterStorage splitterStorage = (SplitterStorage) storage;
            Optional<ReadOnlyPersonAmountBook> personAmountBookOptional;
            Optional<ReadOnlyReceiptBook> receiptBookOptional;
            ReadOnlyPersonAmountBook initialPersonData;
            ReadOnlyReceiptBook initialReceiptData;
            try {
                personAmountBookOptional = splitterStorage.readPersonAmountBook();
                receiptBookOptional = splitterStorage.readReceiptBook();
                if (personAmountBookOptional.isEmpty()) {
                    logger.info("Data file not found. Will be starting with a sample PersonAmountBook");
                    initialPersonData = new PersonAmountBook();
                } else {
                    initialPersonData = personAmountBookOptional.get();
                }
                if (receiptBookOptional.isEmpty()) {
                    logger.info("Data file not found. Will be starting with a sample ReceiptBook");
                    initialReceiptData = new ReceiptBook();
                } else {
                    initialReceiptData = receiptBookOptional.get();
                }
            } catch (DataConversionException e) {
                logger.warning("Data file not in the correct format. Will be starting with an empty "
                        + "PersonAmountBook and ReceiptBook");
                initialPersonData = new PersonAmountBook();
                initialReceiptData = new ReceiptBook();
            } catch (IOException e) {
                logger.warning("Problem while reading from the file. Will be starting with an empty "
                        + "PersonAmountBook and ReceiptBook");
                initialPersonData = new PersonAmountBook();
                initialReceiptData = new ReceiptBook();
            }
            return new SplitterModelManager(initialReceiptData, initialPersonData, userPrefs);

        default:
            throw new InitialiseException(MESSAGE_INITIATION_FAILED);
        }
    }

    /**
     * Returns a {@code LogicManager} with the {@code model}'s EYLAH and {@code storage}. <br>
     *
     * @param mode current mode of EYLAH
     * @return a logic manager
     */
    private Logic initLogicManager(Mode mode) throws InitialiseException {
        switch (mode) {
        case DIET:
            return new DietLogicManager(((DietModel) model), ((DietStorage) storage));
        case SPLITTER:
            return new SplitterLogicManager(((SplitterModel) model), ((SplitterStorage) storage));
        default:
            throw new InitialiseException(MESSAGE_INITIATION_FAILED);
        }
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig() {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PersonAmountBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
            //myselfStorage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

}
