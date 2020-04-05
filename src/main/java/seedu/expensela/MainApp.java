package seedu.expensela;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.expensela.commons.core.Config;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.commons.core.Version;
import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.commons.util.ConfigUtil;
import seedu.expensela.commons.util.StringUtil;
import seedu.expensela.logic.Logic;
import seedu.expensela.logic.LogicManager;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ReadOnlyGlobalData;
import seedu.expensela.model.ReadOnlyUserPrefs;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.util.SampleDataUtil;
import seedu.expensela.storage.ExpenseLaStorage;
import seedu.expensela.storage.GlobalDataStorage;
import seedu.expensela.storage.JsonExpenseLaStorage;
import seedu.expensela.storage.JsonGlobalDataStorage;
import seedu.expensela.storage.JsonUserPrefsStorage;
import seedu.expensela.storage.Storage;
import seedu.expensela.storage.StorageManager;
import seedu.expensela.storage.UserPrefsStorage;
import seedu.expensela.ui.Ui;
import seedu.expensela.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ExpenseLa ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GlobalDataStorage globalDataStorage = new JsonGlobalDataStorage(userPrefs.getGlobalDataFilePath());
        GlobalData globalData = initGlobalData(globalDataStorage);
        ExpenseLaStorage expenseLaStorage = new JsonExpenseLaStorage(userPrefs.getExpenseLaFilePath());
        storage = new StorageManager(expenseLaStorage, userPrefsStorage, globalDataStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs, globalData);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s expensela and {@code userPrefs}. <br>
     * The data from the sample expensela will be used instead if {@code storage}'s expensela is not found,
     * or an empty expensela will be used instead if errors occur when reading {@code storage}'s expensela.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs, ReadOnlyGlobalData globalData) {
        Optional<ReadOnlyExpenseLa> expenseLaOptional;
        ReadOnlyExpenseLa initialData;
        try {
            expenseLaOptional = storage.readExpenseLa();
            if (!expenseLaOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ExpenseLa");
            }
            initialData = expenseLaOptional.orElseGet(SampleDataUtil::getSampleExpenseLa);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExpenseLa");
            initialData = new ExpenseLa();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseLa");
            initialData = new ExpenseLa();
        }

        return new ModelManager(initialData, userPrefs, globalData);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseLa");
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
     * Returns a {@code GlobalData} using the file at {@code storage}'s global data's file path,
     * or a new {@code GlobalData} with default configuration if errors occur when
     * reading from the file.
     */
    protected GlobalData initGlobalData(GlobalDataStorage storage) {
        Path globalDataFilePath = storage.getGlobalDataFilePath();
        logger.info("Using global data file : " + globalDataFilePath);

        GlobalData initializedGlobalData;
        try {
            Optional<GlobalData> globalDataOptional = storage.readGlobalData();
            initializedGlobalData = globalDataOptional.orElse(new GlobalData());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + globalDataFilePath + " is not in the correct format. "
                    + "Using default global data");
            initializedGlobalData = new GlobalData();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseLa");
            initializedGlobalData = new GlobalData();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveGlobalData(initializedGlobalData);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedGlobalData;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ExpenseLa " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ExpenseLa ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.saveGlobalData(model.getGlobalData());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
