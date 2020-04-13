package com.notably;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.notably.commons.Config;
import com.notably.commons.LogsCenter;
import com.notably.commons.Version;
import com.notably.commons.exceptions.DataConversionException;
import com.notably.commons.util.ConfigUtil;
import com.notably.commons.util.StringUtil;
import com.notably.logic.Logic;
import com.notably.logic.LogicManager;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.userpref.ReadOnlyUserPrefModel;
import com.notably.model.userpref.UserPrefModel;
import com.notably.model.userpref.UserPrefModelImpl;
import com.notably.model.util.SampleDataUtil;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.storage.BlockStorage;
import com.notably.storage.JsonBlockStorage;
import com.notably.storage.JsonUserPrefsStorage;
import com.notably.storage.Storage;
import com.notably.storage.StorageManager;
import com.notably.storage.UserPrefsStorage;
import com.notably.view.View;
import com.notably.view.ViewManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected View view;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Notably ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefModel userPrefs = initPrefs(userPrefsStorage);
        BlockStorage blockStorage = new JsonBlockStorage(userPrefs.getBlockDataFilePath());
        storage = new StorageManager(blockStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        view = new ViewManager(logic, model);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s block data and {@code userPrefs}. <br>
     * The data from the sample block data will be used instead if {@code storage}'s block data is not found,
     * or an empty {@code BlockTree} will be used instead if errors occur when reading {@code storage}'s block data.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefModel userPrefs) {
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();

        Optional<BlockModel> blockModelOptional;
        BlockModel initialData;
        try {
            blockModelOptional = storage.readBlockModel();
            if (!blockModelOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample BlockTree");
            }
            initialData = blockModelOptional.orElseGet(SampleDataUtil::getSampleBlockModel);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty BlockTree");
            initialData = new BlockModelImpl();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BlockTree");
            initialData = new BlockModelImpl();
        }
        blockModel.resetData(initialData);
        return new ModelManager(blockModel, suggestionModel, viewStateModel , userPrefs);
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
    protected UserPrefModel initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefModel initializedPrefs;
        try {
            Optional<UserPrefModel> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefModelImpl());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefModelImpl();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BlockTree");
            initializedPrefs = new UserPrefModelImpl();
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
        logger.info("Starting Notably " + MainApp.VERSION);
        view.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Notably ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefModel());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
