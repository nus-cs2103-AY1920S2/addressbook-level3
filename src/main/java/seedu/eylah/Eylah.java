package seedu.eylah;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.Config;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.core.Version;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.util.ConfigUtil;
import seedu.eylah.commons.util.StringUtil;
import seedu.eylah.diettracker.logic.Logic;
import seedu.eylah.diettracker.logic.LogicManager;
import seedu.eylah.diettracker.logic.commands.CommandResult;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.ModelManager;
import seedu.eylah.diettracker.storage.FoodBookStorage;
import seedu.eylah.diettracker.storage.JsonFoodBookStorage;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.Receipt;
import seedu.eylah.expensesplitter.model.UserPrefs;
import seedu.eylah.expensesplitter.model.util.SamplePersonAmountDataUtil;
import seedu.eylah.expensesplitter.storage.JsonPersonAmountBookStorage;
import seedu.eylah.expensesplitter.storage.JsonUserPrefsStorage;
import seedu.eylah.expensesplitter.storage.PersonAmountStorage;
import seedu.eylah.expensesplitter.storage.StorageManager;
import seedu.eylah.expensesplitter.storage.UserPrefsStorage;
import seedu.eylah.ui.Ui;

/**
 * The main entry for the EYLAH.
 */
public class Eylah {

    public static final Version VERSION = new Version(0, 1, 0, true);

    private static final Logger logger = LogsCenter.getLogger(Eylah.class);

    protected Logic dietLogic;
    protected Model dietModel;
    protected FoodBookStorage dietPath;
    protected seedu.eylah.expensesplitter.logic.Logic splitterLogic;
    protected seedu.eylah.expensesplitter.model.Model splitterModel;

    private Ui ui;

    public Eylah() {
        logger.info("=============================[ Initializing EYLAH ]===========================");
        ui = new Ui();
    }


    /**
     * Main method to run the application.
     * For now just ignore these messy code, this is a temporary code for testing purpose.
     * After start the app, only can choose to run in diet or splitter mode,
     * the way to exit the app just simply close the app in terminal.
     *
     */
    public void run() {
        boolean isExit = false;
        ui.showWelcome();
        System.out.println("Enter mode (diet/splitting): ");
        String input = ui.readCommand();
        if (input.equals("diet")) {
            // Diet mode
            Config config;

            config = initConfig(null);

            seedu.eylah.diettracker.storage.UserPrefsStorage userPrefsStorage =
                    new seedu.eylah.diettracker.storage.JsonUserPrefsStorage(config.getUserPrefsFilePath());
            seedu.eylah.diettracker.model.UserPrefs userPrefs = initPrefsDiet(userPrefsStorage);
            FoodBookStorage foodBookStorage = new JsonFoodBookStorage(userPrefs.getFoodBookFilePath());
            seedu.eylah.diettracker.storage.Storage storage =
                    new seedu.eylah.diettracker.storage.StorageManager(foodBookStorage, userPrefsStorage);

            logger.info("Entering Diet MODE.");
            dietModel = initModelManagerDiet(storage, userPrefs);
            dietLogic = new LogicManager(dietModel, storage);
            while (!isExit) {
                System.out.println("Enter Diet Command: ");
                input = ui.readCommand();
                if (input.equals("exit")) {
                    ui.showExit();
                    break;
                }
                try {
                    CommandResult commandResult = dietLogic.execute(input);
                    // Here will print out the respond to user
                    ui.showResult(commandResult.getFeedbackToUser());
                } catch (CommandException | ParseException e) {
                    ui.showError(e.getMessage());
                }
            }
        } else {
            // Splitting mode
            System.out.println("Entering Splitting MODE.");
            Config config;
            PersonAmountStorage storage;


            config = initConfig(null);

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
            UserPrefs userPrefs = initPrefs(userPrefsStorage);

            PersonAmountStorage personAmountStorage =
                    new JsonPersonAmountBookStorage(userPrefs.getPersonAmountBookFilePath());
            storage = new StorageManager(personAmountStorage, userPrefsStorage);

            splitterModel = initModelManager(storage, userPrefs);


            splitterLogic = new seedu.eylah.expensesplitter.logic.LogicManager(splitterModel, storage);
            while (!isExit) {
                System.out.println("Enter Splitting Command: ");
                input = ui.readCommand();
                if (input.equals("exit")) {
                    ui.showExit();
                    break;
                }
                try {
                    seedu.eylah.expensesplitter.logic.commands.CommandResult commandResult =
                            splitterLogic.execute(input);
                    // Here will print out the respond to user
                    ui.showResult(commandResult.getFeedbackToUser());
                } catch (seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException
                        | seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException e) {
                    ui.showError(e.getMessage());
                }
            }
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s personamountbook and {@code userPrefs}. <br>
     * The data from the sample personamount book will be used instead if {@code storage}'s personamountbook
     * is not found,
     * or an personamount book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private seedu.eylah.expensesplitter.model.Model initModelManager(PersonAmountStorage storage, ReadOnlyUserPrefs
        userPrefs) {

        Optional<ReadOnlyPersonAmountBook> personAmountBookOptional;
        ReadOnlyPersonAmountBook initialData;
        try {

            personAmountBookOptional = storage.readPersonAmountBook();
            if (!personAmountBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample PersonAmountBook");
            }
            initialData = personAmountBookOptional.orElseGet(SamplePersonAmountDataUtil::getSamplePersonAmountBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty PersonAmountBook");
            initialData = new PersonAmountBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PersonAmountBook");
            initialData = new PersonAmountBook();
        }

        return new seedu.eylah.expensesplitter.model.ModelManager(new Receipt(), initialData, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private seedu.eylah.diettracker.model.Model initModelManagerDiet(FoodBookStorage storage,
                                                         seedu.eylah.diettracker.model.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.eylah.diettracker.model.ReadOnlyFoodBook> foodBookOptional;
        seedu.eylah.diettracker.model.ReadOnlyFoodBook initialData;
        try {
            foodBookOptional = storage.readFoodBook();
            if (!foodBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample FoodBook");
            }
            initialData =
                    foodBookOptional.orElseGet(seedu.eylah.diettracker.model.util.SampleDataUtil::getSampleFoodBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FoodBook");
            initialData = new FoodBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FoodBook");
            initialData = new FoodBook();
        }

        return new ModelManager(initialData, userPrefs);
    }




    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

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
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected seedu.eylah.diettracker.model.UserPrefs
        initPrefsDiet(seedu.eylah.diettracker.storage.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        seedu.eylah.diettracker.model.UserPrefs initializedPrefs;
        try {
            Optional<seedu.eylah.diettracker.model.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.eylah.diettracker.model.UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.eylah.diettracker.model.UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.eylah.diettracker.model.UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }


    /**
     * Starting point for whole application.
     */
    public static void main(String[] args) throws Exception {
        Eylah eylah = new Eylah();
        eylah.run();
    }
}
