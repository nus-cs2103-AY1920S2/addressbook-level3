package seedu.address;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.AppLogic;
import seedu.address.logic.AppLogicManager;
import seedu.address.ui.AppUiManager;
import seedu.address.ui.Ui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class ContactTracingMainApp extends Application {
    public static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(ContactTracingMainApp.class);

    protected Ui ui;
    protected AppLogic logic;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Contact tracing app ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        this.config = initConfig(appParameters.getConfigPath());
        initLogging(this.config);

        this.logic      = new AppLogicManager();
        this.ui         = new AppUiManager(this.logic);
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

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Contact tracking app " + ContactTracingMainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Contact tracking app ] =============================");
    }
}
