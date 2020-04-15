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

import seedu.address.model.AddressBook;
import seedu.address.model.EventSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventSchedule;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlySchoolworkTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestaurantBook;
import seedu.address.model.SchoolworkTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.EventScheduleStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonEventScheduleStorage;
import seedu.address.storage.JsonRestaurantBookStorage;
import seedu.address.storage.JsonSchoolworkTrackerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.RestaurantBookStorage;
import seedu.address.storage.SchoolworkTrackerStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        RestaurantBookStorage restaurantBookStorage =
                new JsonRestaurantBookStorage(userPrefs.getRestaurantBookFilePath());
        SchoolworkTrackerStorage schedulerStorage =
            new JsonSchoolworkTrackerStorage(userPrefs.getSchedulerFilePath());
        EventScheduleStorage eventScheduleStorage = new JsonEventScheduleStorage(userPrefs.getEventScheduleFilePath());
        storage = new StorageManager(addressBookStorage, restaurantBookStorage, schedulerStorage,
                eventScheduleStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        Optional<ReadOnlyRestaurantBook> restaurantBookOptional;
        ReadOnlyAddressBook initialPersonsData;
        ReadOnlyRestaurantBook initialRestaurantsData;

        Optional<ReadOnlySchoolworkTracker> schedulerOptional;
        Optional<ReadOnlyEventSchedule> eventScheduleOptional;
        ReadOnlySchoolworkTracker initialAssignmentsData;
        ReadOnlyEventSchedule initialEventsData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialPersonsData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialPersonsData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialPersonsData = new AddressBook();
        }

        try {
            restaurantBookOptional = storage.readRestaurantBook();
            if (!restaurantBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample RestaurantBook");
            }
            initialRestaurantsData = restaurantBookOptional.orElseGet(SampleDataUtil::getSampleRestaurantBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RestaurantBook");
            initialRestaurantsData = new RestaurantBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RestaurantBook");
            initialRestaurantsData = new RestaurantBook();
        }

        try {
            schedulerOptional = storage.readSchoolworkTracker();
            if (!schedulerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty SchoolworkTracker.");
            }
            initialAssignmentsData = schedulerOptional.orElse(new SchoolworkTracker());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty SchoolworkTracker");
            initialAssignmentsData = new SchoolworkTracker();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty SchoolworkTracker");
            initialAssignmentsData = new SchoolworkTracker();
        }

        try {
            eventScheduleOptional = storage.readEventSchedule();
            if (!eventScheduleOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty Events Schedule.");
            }
            initialEventsData = eventScheduleOptional.orElse(new EventSchedule());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Events Schedule");
            initialEventsData = new EventSchedule();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Events Schedule");
            initialEventsData = new EventSchedule();
        }

        return new ModelManager(initialPersonsData, initialRestaurantsData, initialAssignmentsData,
                initialEventsData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
