package fithelper;

import static fithelper.model.util.SampleDataUtil.getSampleFitHelper;

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
import fithelper.model.util.SampleDataUtil;
import fithelper.storage.FitHelperStorage;
import fithelper.storage.JsonFitHelperStorage;
import fithelper.storage.JsonUserPrefsStorage;
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
    protected FitHelperStorage storage = new JsonFitHelperStorage(Config.FITHELPER_DATA_PATH);
    //protected UserProfileStorage userProfileStorage = new JsonUserProfileStorage(Config.USERPROFILE_DATA_PATH);
    protected Model model;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing FitHelper ]===========================");
        super.init();

        model = initModelManager(storage);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(FitHelperStorage storage) {
        Optional<ReadOnlyFitHelper> fitHelperOptional;
        ReadOnlyFitHelper initialData;
        try {
            fitHelperOptional = storage.readFitHelper();
            if (!fitHelperOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample FitHelper");
            }
            initialData = fitHelperOptional.orElseGet(SampleDataUtil::getSampleFitHelper);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FitHelper");
            initialData = getSampleFitHelper();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FitHelper");
            initialData = getSampleFitHelper();
        }
        return new ModelManager(initialData);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting FitHelper " + MainApp.VERSION);
        ui.start(primaryStage);
    }
}

