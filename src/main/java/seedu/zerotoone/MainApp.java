package seedu.zerotoone;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.zerotoone.commons.core.Config;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.core.Version;
import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.commons.util.ConfigUtil;
import seedu.zerotoone.commons.util.StringUtil;
import seedu.zerotoone.logic.Logic;
import seedu.zerotoone.logic.LogicManager;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.SessionList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.util.SampleExerciseDataUtil;
import seedu.zerotoone.model.util.SampleScheduleDataUtil;
import seedu.zerotoone.model.util.SampleSessionDataUtil;
import seedu.zerotoone.model.util.SampleWorkoutDataUtil;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.storage.Storage;
import seedu.zerotoone.storage.StorageManager;
import seedu.zerotoone.storage.exercise.ExerciseListStorage;
import seedu.zerotoone.storage.exercise.ExerciseListStorageManager;
import seedu.zerotoone.storage.schedule.ScheduleListStorage;
import seedu.zerotoone.storage.schedule.ScheduleListStorageManager;
import seedu.zerotoone.storage.session.SessionListStorage;
import seedu.zerotoone.storage.session.SessionListStorageManager;
import seedu.zerotoone.storage.userprefs.UserPrefsStorage;
import seedu.zerotoone.storage.userprefs.UserPrefsStorageManager;
import seedu.zerotoone.storage.workout.WorkoutListStorage;
import seedu.zerotoone.storage.workout.WorkoutListStorageManager;
import seedu.zerotoone.ui.Ui;
import seedu.zerotoone.ui.UiManager;

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
        logger.info("=============================[ Initializing ZeroToOne ]===========================");
        super.init();

        // -----------------------------------------------------------------------------------------
        // Common
        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        UserPrefsStorage userPrefsStorage = new UserPrefsStorageManager(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        initLogging(config);

        // -----------------------------------------------------------------------------------------
        // Exercise List
        ExerciseListStorage exerciseListStorage = new ExerciseListStorageManager(userPrefs.getExerciseListFilePath());
        // Workout List
        WorkoutListStorage workoutListStorage = new WorkoutListStorageManager(userPrefs.getWorkoutListFilePath());
        // Schedule
        ScheduleListStorage scheduleListStorage = new ScheduleListStorageManager(userPrefs.getScheduleListFilePath());
        // Session
        SessionListStorage sessionListStorage = new SessionListStorageManager(userPrefs.getLogListFilePath());

        // -----------------------------------------------------------------------------------------
        // Common
        storage = new StorageManager(userPrefsStorage,
                exerciseListStorage,
                workoutListStorage,
                scheduleListStorage,
                sessionListStorage);
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s ZeroToOne storage
     * and {@code userPrefs}. <br> The data from the sample ZeroToOne storage will
     * be used instead if {@code storage}'s ZeroToOne storage is not found, or an empty ZeroToOne
     * storage will be used instead if errors occur when reading {@code storage}'s ZeroToOne storage.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyExerciseList> exerciseListOptional;
        ReadOnlyExerciseList initialExerciseListData;

        Optional<ScheduleList> scheduleListOptional;
        ScheduleList initialScheduleListData;

        Optional<ReadOnlyWorkoutList> workoutListOptional;
        ReadOnlyWorkoutList initialWorkoutListData;

        Optional<ReadOnlySessionList> sessionListOptional;
        ReadOnlySessionList initialSessionListData;

        // -----------------------------------------------------------------------------------------
        // Exercise List
        try {
            exerciseListOptional = storage.readExerciseList();
            if (!exerciseListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ExerciseList");
            }
            initialExerciseListData = exerciseListOptional.orElseGet(SampleExerciseDataUtil::getSampleExerciseList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExerciseList");
            initialExerciseListData = new ExerciseList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseList");
            initialExerciseListData = new ExerciseList();
        }

        // Workout List
        try {
            workoutListOptional = storage.readWorkoutList();
            if (!workoutListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample WorkoutList");
            }
            initialWorkoutListData = workoutListOptional.orElseGet(SampleWorkoutDataUtil::getSampleWorkoutList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty WorkoutList");
            initialWorkoutListData = new WorkoutList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty WorkoutList");
            initialWorkoutListData = new WorkoutList();
        }

        // Schedule List
        try {
            scheduleListOptional = storage.readScheduleList();
            if (!scheduleListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty ScheduleList");
            }
            initialScheduleListData = scheduleListOptional.orElseGet(SampleScheduleDataUtil::getSampleScheduleList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ScheduleList");
            initialScheduleListData = new ScheduleList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ScheduleList");
            initialScheduleListData = new ScheduleList();
        }

        // Session List
        try {
            sessionListOptional = storage.readSessionList();
            if (sessionListOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample ExerciseList");
            }
            initialSessionListData = sessionListOptional.orElseGet(SampleSessionDataUtil::getSampleSessionList);

            try {
                storage.saveSessionList(initialSessionListData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty SessionList");
            initialSessionListData = new SessionList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty SessionList");
            initialSessionListData = new SessionList();
        }

        return new ModelManager(userPrefs,
                initialExerciseListData,
                initialWorkoutListData,
                initialScheduleListData,
                initialSessionListData);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseList");
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
        logger.info("Starting ZeroToOne " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ZeroToOne ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
