package team.easytravel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import team.easytravel.commons.core.Config;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.commons.core.Version;
import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.commons.util.ConfigUtil;
import team.easytravel.commons.util.StringUtil;
import team.easytravel.logic.Logic;
import team.easytravel.logic.LogicManager;
import team.easytravel.model.Model;
import team.easytravel.model.ModelManager;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyUserPrefs;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.sampledata.SampleDataUtil;
import team.easytravel.storage.JsonUserPrefsStorage;
import team.easytravel.storage.Storage;
import team.easytravel.storage.StorageManager;
import team.easytravel.storage.UserPrefsStorage;
import team.easytravel.storage.accommodationbooking.AccommodationBookingStorage;
import team.easytravel.storage.accommodationbooking.JsonAccommodationBookingStorage;
import team.easytravel.storage.activity.ActivityStorage;
import team.easytravel.storage.activity.JsonActivityStorage;
import team.easytravel.storage.fixedexpense.FixedExpenseStorage;
import team.easytravel.storage.fixedexpense.JsonFixedExpenseStorage;
import team.easytravel.storage.packinglist.JsonPackingListStorage;
import team.easytravel.storage.packinglist.PackingListStorage;
import team.easytravel.storage.transportbooking.JsonTransportBookingStorage;
import team.easytravel.storage.transportbooking.TransportBookingStorage;
import team.easytravel.storage.trip.JsonTripManagerStorage;
import team.easytravel.storage.trip.TripManagerStorage;
import team.easytravel.ui.Ui;
import team.easytravel.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    /**
     * The constant VERSION.
     */
    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * The Ui.
     */
    protected Ui ui;
    /**
     * The Logic.
     */
    protected Logic logic;
    /**
     * The Storage.
     */
    protected Storage storage;
    /**
     * The Model.
     */
    protected Model model;
    /**
     * The Config.
     */
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing E.T. ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        TransportBookingStorage transportBookingStorage =
                new JsonTransportBookingStorage((userPrefs.getTransportBookingStorageFilePath()));
        FixedExpenseStorage fixedExpenseStorage =
                new JsonFixedExpenseStorage(userPrefs.getFixedExpenseStorageFilePath());
        ActivityStorage activityStorage =
                new JsonActivityStorage(userPrefs.getActivityStorageFilePath());
        AccommodationBookingStorage accommodationBookingStorage =
                new JsonAccommodationBookingStorage((userPrefs.getAccommodationBookingStorageFilePath()));
        PackingListStorage packingListStorage = new JsonPackingListStorage(userPrefs.getPackingListStorageFilePath());
        TripManagerStorage tripManagerStorage = new JsonTripManagerStorage(userPrefs.getTripStorageFilePath());

        storage = new StorageManager(
                transportBookingStorage,
                fixedExpenseStorage,
                activityStorage,
                accommodationBookingStorage,
                packingListStorage,
                tripManagerStorage,
                userPrefsStorage
        );

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage} files and {@code userPrefs}. <br>
     * The data from the sample Easy Travel will be used instead if {@code storage} files are not found,
     * or an empty Easy Travel App will be used instead if errors occur when reading {@code storage} files.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        TripManager tripManager = initTripManager(storage);
        ReadOnlyTransportBookingManager transportBookingManager = initTransportBookingManager(storage);
        ReadOnlyFixedExpenseManager fixedExpenseManager = initFixedExpenseManager(storage);
        ReadOnlyActivityManager activityManager = initActivityManager(storage);
        ReadOnlyAccommodationBookingManager accommodationBookingManager = initAccommodationBookingManager(storage);
        ReadOnlyPackingListManager packingListManager = initPackingListManager(storage);

        return new ModelManager(transportBookingManager, fixedExpenseManager, packingListManager,
                activityManager, accommodationBookingManager, tripManager, userPrefs);
    }

    /**
     * Returns a {@code ReadOnlyTransportBookingManager} with the data from {@code storage}'s transport bookings.
     * The data from the sample transport bookings will be used instead
     * if {@code storage}'s transport booking manager is not found,
     * or an empty transport booking manager will be used instead if errors
     * occur when reading {@code storage}'s transport booking manager.
     */
    private ReadOnlyTransportBookingManager initTransportBookingManager(Storage storage) {
        ReadOnlyTransportBookingManager transportBookingManager;
        try {
            Optional<ReadOnlyTransportBookingManager> transportBookingManagerOptional = storage.readTransportBookings();
            if (transportBookingManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample TransportBookingManager.");
            }
            transportBookingManager =
                    transportBookingManagerOptional.orElseGet(SampleDataUtil::getSampleTransportBookingManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "TransportBookingManager.");
            transportBookingManager = new TransportBookingManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "TransportBookingManager.");
            transportBookingManager = new TransportBookingManager();
        }

        try {
            storage.saveTransportBookings(transportBookingManager);
            logger.info("Saving initial data of TransportBookingManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return transportBookingManager;
    }

    /**
     * Returns a {@code ReadOnlyFixedExpenseManager} with the data from {@code storage}'s fixed expenses.
     * The data from the sample fixed expenses will be used instead
     * if {@code storage}'s fixed expense manager is not found,
     * or an empty fixed expense manager will be used instead
     * if errors occur when reading {@code storage}'s fixed expense manager.
     */
    private ReadOnlyFixedExpenseManager initFixedExpenseManager(Storage storage) {
        ReadOnlyFixedExpenseManager fixedExpenseManager;
        try {
            Optional<ReadOnlyFixedExpenseManager> fixedExpenseManagerOptional = storage.readFixedExpenses();
            if (fixedExpenseManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample FixedExpenseManager.");
            }
            fixedExpenseManager = fixedExpenseManagerOptional.orElseGet(SampleDataUtil::getSampleFixedExpenseManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "FixedExpenseManager.");
            fixedExpenseManager = new FixedExpenseManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "FixedExpenseManager.");
            fixedExpenseManager = new FixedExpenseManager();
        }

        try {
            storage.saveFixedExpenses(fixedExpenseManager);
            logger.info("Saving initial data of FixedExpenseManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return fixedExpenseManager;
    }

    /**
     * Returns a {@code ReadOnlyActivityManager} with the data from {@code storage}'s activities.
     * The data from the sample activities will be used instead if {@code storage}'s activities manager is not found,
     * or an empty activity manager will be used instead if errors occur when reading {@code storage}'s
     * activity manager.
     */
    private ReadOnlyActivityManager initActivityManager(Storage storage) {
        ReadOnlyActivityManager activityManager;
        try {
            Optional<ReadOnlyActivityManager> activityManagerOptional = storage.readActivityManager();
            if (activityManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample ActivityManager.");
            }
            activityManager =
                    activityManagerOptional.orElseGet(SampleDataUtil::getSampleActivityManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "ActivityManager.");
            activityManager = new ActivityManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "ActivityManager.");
            activityManager = new ActivityManager();
        }

        try {
            storage.saveActivityManager(activityManager);
            logger.info("Saving initial data of ActivityManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return activityManager;
    }

    /**
     * Returns a {@code ReadOnlyAccommodationBookingManager} with the data from {@code storage}'s accommodation
     * bookings. The data from the sample accommodation bookings will be used instead
     * if {@code storage}'s accommodation booking manager is not found,
     * or an empty accommodation booking manager will be used instead if errors occur when
     * reading {@code storage}'s accommodation booking manager.
     */
    private ReadOnlyAccommodationBookingManager initAccommodationBookingManager(Storage storage) {
        ReadOnlyAccommodationBookingManager accommodationBookingManager;
        try {
            Optional<ReadOnlyAccommodationBookingManager> accommodationBookingManagerOptional =
                    storage.readAccommodationBookings();
            if (accommodationBookingManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample AccommodationBookingManager.");
            }
            accommodationBookingManager =
                    accommodationBookingManagerOptional.orElseGet(SampleDataUtil::getSampleAccommodationBookingManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "AccommodationBookingManager.");
            accommodationBookingManager = new AccommodationBookingManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "AccommodationBookingManager.");
            accommodationBookingManager = new AccommodationBookingManager();
        }

        try {
            storage.saveAccommodationBookings(accommodationBookingManager);
            logger.info("Saving initial data of AccommodationBookingManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return accommodationBookingManager;
    }

    /**
     * Returns a {@code ReadOnlyPackingListManager} with the data from {@code storage}'s packing list.
     * The data from the sample packing list will be used instead if {@code storage}'s packing list
     * is not found, or an empty packing list will be used instead if errors occur when
     * reading {@code storage}'s packing list.
     */
    private ReadOnlyPackingListManager initPackingListManager(Storage storage) {
        ReadOnlyPackingListManager packingListManager;
        try {
            Optional<ReadOnlyPackingListManager> packingListManagerOptional = storage.readPackingList();
            if (packingListManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample PackingListManager.");
            }
            packingListManager =
                    packingListManagerOptional.orElseGet(SampleDataUtil::getSamplePackingListManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "PackingListManager.");
            packingListManager = new PackingListManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "PackingListManager.");
            packingListManager = new PackingListManager();
        }

        try {
            storage.savePackingList(packingListManager);
            logger.info("Saving initial data of PackingListManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return packingListManager;
    }

    /**
     * Returns a {@code TripManager} with the data from {@code storage}'s trip.
     * An empty transport booking manager will be used instead if errors
     * occur when reading {@code storage}'s transport booking manager.
     */
    private TripManager initTripManager(Storage storage) {
        TripManager tripManager;
        try {
            Optional<TripManager> tripManagerOptional = storage.readTripManager();
            if (tripManagerOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample TransportBookingManager.");
                tripManager = new TripManager();
            } else {
                tripManager = tripManagerOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty"
                    + "TripManager.");
            tripManager = new TripManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "TripManager.");
            tripManager = new TripManager();
        }

        try {
            storage.saveTripManager(tripManager);
            logger.info("Saving initial data of TripManager.");
        } catch (IOException e) {
            logger.warning("Problem while saving to the file.");
        }

        return tripManager;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     *
     * @param configFilePath the config file path
     * @return the config
     */
    protected Config initConfig(Path configFilePath) {
        Path configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        Config initializedConfig;
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are new/unused fields
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
     *
     * @param storage the storage
     * @return the user prefs
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Easy Travel App");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting E.T. " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping E.T. ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
