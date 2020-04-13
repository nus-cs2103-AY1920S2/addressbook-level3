package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.consult.ReadOnlyConsult;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.mod.ReadOnlyMod;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.StudentTAble;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.ConsultStorage;
import seedu.address.storage.JsonConsultStorage;
import seedu.address.storage.JsonModStorage;
import seedu.address.storage.JsonReminderStorage;
import seedu.address.storage.JsonStudentStorage;
import seedu.address.storage.JsonTutorialStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.ModStorage;
import seedu.address.storage.ReminderStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.StudentStorage;
import seedu.address.storage.TutorialStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TAble ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        StudentStorage studentStorage = new JsonStudentStorage(userPrefs.getStudentTAbleFilePath());
        ConsultStorage consultStorage = new JsonConsultStorage(userPrefs.getConsultTAbleFilePath());
        TutorialStorage tutorialStorage = new JsonTutorialStorage(userPrefs.getTutorialTAbleFilePath());
        ModStorage modStorage = new JsonModStorage(userPrefs.getModTAbleFilePath());
        ReminderStorage reminderStorage = new JsonReminderStorage(userPrefs.getReminderTAbleFilePath());
        storage = new StorageManager(studentStorage, userPrefsStorage, consultStorage,
            tutorialStorage, modStorage, reminderStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s TAble and {@code userPrefs}. <br>
     * The data from the sample TAble will be used instead if {@code storage}'s TAble is not found,
     * or an empty TAble will be used instead if errors occur when reading {@code storage}'s TAble.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyStudent> addressBookOptional;
        Optional<ReadOnlyConsult> consultsOptional;
        Optional<ReadOnlyTutorial> tutorialsOptional;
        Optional<ReadOnlyMod> modsOptional;
        Optional<ReadOnlyReminder> remindersOptional;

        ReadOnlyStudent initialData;
        ReadOnlyConsult initialConsults;
        ReadOnlyTutorial initialTutorials;
        ReadOnlyMod initialMods;
        ReadOnlyReminder initialReminders;

        try {
            addressBookOptional = storage.readStudentTAble();
            consultsOptional = storage.readConsults();
            tutorialsOptional = storage.readTutorials();
            modsOptional = storage.readMods();
            remindersOptional = storage.readReminders();

            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample StudentTAble");
            }
            if (!consultsOptional.isPresent()) {
                logger.info("Consults file not found. Will be starting with a sample consultTAble");
            }
            if (!tutorialsOptional.isPresent()) {
                logger.info("Tutorials file not found. Will be starting with a sample tutorialTAble");
            }
            if (!modsOptional.isPresent()) {
                logger.info("Modules file not found. Will be starting with a sample modTAble");
            }
            if (!remindersOptional.isPresent()) {
                logger.info("Reminders file not found. Will be starting with a sample reminderTAble");
            }

            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            initialConsults = consultsOptional.orElseGet(SampleDataUtil::getSampleConsults);
            initialTutorials = tutorialsOptional.orElseGet(SampleDataUtil::getSampleTutorials);
            initialMods = modsOptional.orElseGet(SampleDataUtil::getSampleMods);
            initialReminders = remindersOptional.orElseGet(SampleDataUtil::getSampleReminders);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TAble");
            initialData = new StudentTAble();
            initialConsults = new ConsultTAble();
            initialTutorials = new TutorialTAble();
            initialMods = new ModTAble();
            initialReminders = new ReminderTAble();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TAble");
            initialData = new StudentTAble();
            initialConsults = new ConsultTAble();
            initialTutorials = new TutorialTAble();
            initialMods = new ModTAble();
            initialReminders = new ReminderTAble();
        }

        return new ModelManager(initialData, userPrefs, initialConsults,
                initialTutorials, initialMods, initialReminders);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty StudentTAble");
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
        logger.info("Starting TAble " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping TAble ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
