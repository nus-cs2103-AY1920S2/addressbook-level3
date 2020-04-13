package seedu.recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.recipe.commons.core.Config;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.core.Version;
import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.commons.util.ConfigUtil;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.logic.Logic;
import seedu.recipe.logic.LogicManager;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.model.util.SampleDataUtil;
import seedu.recipe.storage.Storage;
import seedu.recipe.storage.StorageManager;
import seedu.recipe.storage.achievement.JsonQuoteBookStorage;
import seedu.recipe.storage.achievement.QuoteBookStorage;
import seedu.recipe.storage.cooked.CookedRecordBookStorage;
import seedu.recipe.storage.cooked.JsonCookedRecordBookStorage;
import seedu.recipe.storage.plan.JsonPlannedBookStorage;
import seedu.recipe.storage.plan.PlannedBookStorage;
import seedu.recipe.storage.recipe.JsonRecipeBookStorage;
import seedu.recipe.storage.recipe.RecipeBookStorage;
import seedu.recipe.storage.userpref.JsonUserPrefsStorage;
import seedu.recipe.storage.userpref.UserPrefsStorage;
import seedu.recipe.ui.Ui;
import seedu.recipe.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing RecipeBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        RecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(userPrefs.getRecipeBookFilePath());
        CookedRecordBookStorage cookedRecordBookStorage = new JsonCookedRecordBookStorage(
                userPrefs.getCookedRecordFilePath());
        PlannedBookStorage plannedBookStorage = new JsonPlannedBookStorage(userPrefs.getPlannedBookFilePath());
        QuoteBookStorage quoteBookStorage = new JsonQuoteBookStorage(userPrefs.getQuoteBookFilePath());
        storage = new StorageManager(recipeBookStorage, cookedRecordBookStorage, plannedBookStorage,
                quoteBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s recipe book and {@code userPrefs}. <br>
     * The data from the sample recipe book will be used instead if {@code storage}'s recipe book is not found,
     * or an empty recipe book will be used instead if errors occur when reading {@code storage}'s recipe book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyRecipeBook> recipeBookOptional;
        ReadOnlyRecipeBook initialData;
        Optional<ReadOnlyCookedRecordBook> recordBookOptional;
        ReadOnlyCookedRecordBook initialRecords;
        Optional<ReadOnlyPlannedBook> plannedBookOptional;
        ReadOnlyPlannedBook initialPlannedData;
        Optional<ReadOnlyQuoteBook> quoteBookOptional;
        ReadOnlyQuoteBook initialQuotes;

        try {
            plannedBookOptional = storage.readPlannedBook();
            if (!plannedBookOptional.isPresent()) {
                logger.info("Data file for planned recipes not found. Will be starting with an empty PlannedBook");
            }
            initialPlannedData = plannedBookOptional.orElse(new PlannedBook());

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty PlannedBook");
            initialPlannedData = new PlannedBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PlannedBook");
            initialPlannedData = new PlannedBook();
        }

        try {
            recipeBookOptional = storage.readRecipeBook();
            if (!recipeBookOptional.isPresent()) {
                logger.info("Data file for recipes not found. Will be starting with a sample RecipeBook"
                    + " and PlannedBook");
                initialPlannedData = new PlannedBook();
            }
            initialData = recipeBookOptional.orElseGet(SampleDataUtil::getSampleRecipeBook);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RecipeBook"
                    + " and PlannedBook");
            initialData = new RecipeBook();
            initialPlannedData = new PlannedBook();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file for recipes. "
                    + "Will be starting with an empty RecipeBook and PlannedBook");
            initialData = new RecipeBook();
            initialPlannedData = new PlannedBook();
        }

        try {
            recordBookOptional = storage.readCookedRecordBook();
            if (!recordBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty RecordBook");
                initialRecords = new CookedRecordBook();
            } else {
                initialRecords = recordBookOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RecordBook");
            initialRecords = new CookedRecordBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RecordBook");
            initialRecords = new CookedRecordBook();
        }

        try {
            quoteBookOptional = storage.readQuoteBook();
            if (!quoteBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Quotebook");
            }
            initialQuotes = quoteBookOptional.orElseGet(SampleDataUtil::getSampleQuoteBook);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Quotebook");
            initialQuotes = new QuoteBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Quotebook");
            initialQuotes = new QuoteBook();
        }

        return new ModelManager(initialData, userPrefs, initialRecords, initialPlannedData, initialQuotes);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty RecipeBook");
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

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting RecipeBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Recipe Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
