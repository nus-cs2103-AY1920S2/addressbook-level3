package seedu.jelphabot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.jelphabot.commons.core.Config;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.core.Version;
import seedu.jelphabot.commons.exceptions.DataConversionException;
import seedu.jelphabot.commons.util.ConfigUtil;
import seedu.jelphabot.commons.util.StringUtil;
import seedu.jelphabot.logic.Logic;
import seedu.jelphabot.logic.LogicManager;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.ReadOnlyUserPrefs;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.util.SampleDataUtil;
import seedu.jelphabot.storage.JelphaBotStorage;
import seedu.jelphabot.storage.JsonJelphaBotStorage;
import seedu.jelphabot.storage.JsonUserPrefsStorage;
import seedu.jelphabot.storage.Storage;
import seedu.jelphabot.storage.StorageManager;
import seedu.jelphabot.storage.UserPrefsStorage;
import seedu.jelphabot.ui.Ui;
import seedu.jelphabot.ui.UiManager;

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
        logger.info("=============================[ Initializing JelphaBot ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        JelphaBotStorage jelphaBotStorage = new JsonJelphaBotStorage(
            userPrefs.getJelphaBotFilePath(), userPrefs.getRemindersFilePath());
        storage = new StorageManager(jelphaBotStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s task list and {@code userPrefs}. <br>
     * The data from the sample task list will be used instead if {@code storage}'s task list is not found,
     * or an empty task list will be used instead if errors occur when reading {@code storage}'s task list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyJelphaBot> jelphaBotOptional;
        Optional<ReadOnlyJelphaBot> jelphaBotReminderOptional;
        ReadOnlyJelphaBot initialData;
        ReadOnlyJelphaBot initialReminderData;
        try {
            jelphaBotOptional = storage.readJelphaBot();
            jelphaBotReminderOptional = storage.readJelphaBot(true);
            if (!jelphaBotOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample JelphaBot");
            }
            initialData = jelphaBotOptional.orElseGet(SampleDataUtil::getSampleJelphaBot);
            initialReminderData = jelphaBotReminderOptional.orElseGet(SampleDataUtil::getSampleJelphaBot);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty JelphaBot");
            initialData = new JelphaBot();
            initialReminderData = new JelphaBot();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty JelphaBot");
            initialData = new JelphaBot();
            initialReminderData = new JelphaBot();
        }

        initialData.setReminders(initialReminderData.getReminderList());
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
            logger.warning("Problem while reading from the file. Will be starting with an empty JelphaBot");
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
        logger.info("Starting JelphaBot " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping JelphaBot ] =============================");

        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
