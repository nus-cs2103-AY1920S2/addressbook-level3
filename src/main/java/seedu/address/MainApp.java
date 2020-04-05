package seedu.address;

import java.io.File;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.academics.Academics;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventHistory;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.notes.NotesManager;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.academics.AcademicsStorage;
import seedu.address.storage.academics.JsonAcademicsStorage;
import seedu.address.storage.admin.AdminStorage;
import seedu.address.storage.admin.JsonAdminStorage;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.event.JsonEventStorage;
import seedu.address.storage.notes.JsonNotesManagerStorage;
import seedu.address.storage.notes.NotesManagerStorage;

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
        AcademicsStorage academicsStorage = new JsonAcademicsStorage(userPrefs.getAcademicsFilePath());
        EventStorage eventStorage = new JsonEventStorage(userPrefs.getEventHistoryFilePath());
        NotesManagerStorage notesManagerStorage = new JsonNotesManagerStorage(userPrefs.getNotesFilePath());
        AdminStorage adminStorage = new JsonAdminStorage(userPrefs.getAdminFilePath());
        storage = new StorageManager(addressBookStorage, adminStorage, academicsStorage,
                 userPrefsStorage, eventStorage, notesManagerStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        File file = new File("images");
        if (!file.exists()) {
            new File("images").mkdir();
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        Optional<ReadOnlyAcademics> academicsOptional;
        Optional<ReadOnlyEvents> eventsOptional;
        Optional<ReadOnlyNotes> notesManagerOptional;
        Optional<ReadOnlyAdmin> adminOptional;


        ReadOnlyAddressBook initialData;
        ReadOnlyAcademics initialAcademics;
        ReadOnlyAdmin initialAdmin;
        ReadOnlyEvents initialEvents;
        ReadOnlyNotes initialNotesManager;
        try {
            addressBookOptional = storage.readAddressBook();
            academicsOptional = storage.readAcademics();
            adminOptional = storage.readAdmin();
            eventsOptional = storage.readEvents();
            notesManagerOptional = storage.readNotesManager();

            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
                new File("data").mkdir();
            }
            if (!academicsOptional.isPresent()) {
                logger.info("Academics file not found. Will be starting with a sample Academics.");
            }
            if (!adminOptional.isPresent()) {
                logger.info("Admin file not found.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            initialAcademics = academicsOptional.orElseGet(SampleDataUtil::getSampleAcademics);
            initialAdmin = adminOptional.orElseGet(SampleDataUtil::getSampleAdmin);

            if (!eventsOptional.isPresent()) {
                logger.info("Events file not found, Will be starting with a sample Events file");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            initialAcademics = academicsOptional.orElseGet(SampleDataUtil::getSampleAcademics);
            initialEvents = eventsOptional.orElseGet(SampleDataUtil::getSampleEvents);
            initialNotesManager = notesManagerOptional.orElseGet(SampleDataUtil::getSampleNotesManager);

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
            initialAcademics = new Academics();
            initialAdmin = new Admin();
            initialEvents = new EventHistory();
            initialNotesManager = new NotesManager();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
            initialAcademics = new Academics();
            initialAdmin = new Admin();
            initialNotesManager = new NotesManager();
            initialEvents = new EventHistory();
            initialNotesManager = new NotesManager();
        }

        return new ModelManager(initialData, initialAcademics, initialAdmin, initialNotesManager, userPrefs,
                initialEvents);
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
