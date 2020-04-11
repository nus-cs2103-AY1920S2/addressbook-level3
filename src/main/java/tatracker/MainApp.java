package tatracker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import tatracker.commons.core.Config;
import tatracker.commons.core.LogsCenter;
import tatracker.commons.core.Notification;
import tatracker.commons.core.Version;
import tatracker.commons.exceptions.DataConversionException;
import tatracker.commons.util.ConfigUtil;
import tatracker.commons.util.StringUtil;
import tatracker.logic.Logic;
import tatracker.logic.LogicManager;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.ReadOnlyUserPrefs;
import tatracker.model.TaTracker;
import tatracker.model.UserPrefs;
import tatracker.model.util.SampleDataUtil;
import tatracker.storage.JsonTaTrackerStorage;
import tatracker.storage.JsonUserPrefsStorage;
import tatracker.storage.Storage;
import tatracker.storage.StorageManager;
import tatracker.storage.TaTrackerStorage;
import tatracker.storage.UserPrefsStorage;
import tatracker.ui.Ui;
import tatracker.ui.UiManager;

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
        logger.info("=============================[ Initializing TaTracker ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        TaTrackerStorage taTrackerStorage = new JsonTaTrackerStorage(userPrefs.getTaTrackerFilePath());
        storage = new StorageManager(taTrackerStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s TA-Tracker and {@code userPrefs}. <br>
     * The data from the sample ta-tracker will be used instead if {@code storage}'s ta-tracker is not found,
     * or an empty ta-tracker will be used instead if errors occur when reading {@code storage}'s ta-tracker.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyTaTracker> taTrackerOptional;
        ReadOnlyTaTracker initialData;
        try {
            taTrackerOptional = storage.readTaTracker();
            if (!taTrackerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaTracker");
            }
            initialData = taTrackerOptional.orElseGet(SampleDataUtil::getSampleTaTracker);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaTracker");
            initialData = new TaTracker();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaTracker");
            initialData = new TaTracker();
        }

        return new ModelManager(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty TaTracker");
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
        logger.info("Starting TaTracker " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping TA-Tracker ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }

        // Dispose the system tray icon
        Notification.dispose();
    }
}
