package fithelper;

import static fithelper.model.util.SampleDataUtil.getSampleFitHelper;
import static fithelper.model.util.SampleDataUtil.getSampleUserProfile;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.Config;
import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Version;
import fithelper.commons.exceptions.DataConversionException;
import fithelper.logic.Logic;
import fithelper.logic.LogicManager;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.util.SampleDataUtil;
import fithelper.storage.FitHelperStorage;
import fithelper.storage.JsonFitHelperStorage;
import fithelper.storage.JsonUserProfileStorage;
import fithelper.storage.UserProfileStorage;
import fithelper.ui.Ui;
import fithelper.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected FitHelperStorage fitHelperStorage = new JsonFitHelperStorage(Config.FITHELPER_DATA_PATH);
    protected UserProfileStorage userProfileStorage = new JsonUserProfileStorage(Config.USERPROFILE_DATA_PATH);
    protected Model model;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing FitHelper ]===========================");
        super.init();

        model = initModelManager(fitHelperStorage, userProfileStorage);
        logic = new LogicManager(model, fitHelperStorage, userProfileStorage);
        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s fithelper and {@code userPrefs}. <br>
     * The data from the sample fithelper will be used instead if {@code storage}'s fithelper is not found,
     * or an empty fithelper will be used instead if errors occur when reading {@code storage}'s fithelper.
     */
    private Model initModelManager(FitHelperStorage fitHelperStorage, UserProfileStorage userProfileStorage) {
        // fithelper initialization.
        Optional<ReadOnlyFitHelper> fitHelperOptional;
        ReadOnlyFitHelper initialData;
        try {
            fitHelperOptional = fitHelperStorage.readFitHelper();
            if (!fitHelperOptional.isPresent()) {
                logger.info("FitHelper data file not found. Will be starting with a sample FitHelper");
            }
            initialData = fitHelperOptional.orElseGet(SampleDataUtil::getSampleFitHelper);
        } catch (DataConversionException e) {
            logger.warning("FitHelper data file not in the correct format. Will be starting with an empty FitHelper");
            initialData = getSampleFitHelper();
        } catch (IOException e) {
            logger.warning("Problem while reading from the fithelper file. Will be starting with an empty FitHelper");
            initialData = getSampleFitHelper();
        }

        // user profile initialization.
        Optional<ReadOnlyUserProfile> userProfileOptional;
        ReadOnlyUserProfile initialUserProfileData;
        try {
            userProfileOptional = userProfileStorage.readUserProfile();
            if (!userProfileOptional.isPresent()) {
                logger.info("User Profile data file not found. Will be starting with a sample User Profile");
            }
            initialUserProfileData = userProfileOptional.orElseGet(SampleDataUtil::getSampleUserProfile);
        } catch (DataConversionException e) {
            logger.warning("User Profile data file not in the correct format. "
                    + "Will be starting with an empty User Profile");
            initialUserProfileData = getSampleUserProfile();
        } catch (IOException e) {
            logger.warning("Problem while reading from the user profile file. "
                    + "Will be starting with an empty User Profile");
            initialUserProfileData = getSampleUserProfile();
        }

        return new ModelManager(initialData, initialUserProfileData);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting FitHelper " + MainApp.VERSION);
        ui.start(primaryStage);
    }
}

