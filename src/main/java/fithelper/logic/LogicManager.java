package fithelper.logic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.logic.parser.FitHelperParser;
import fithelper.model.Model;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;
import fithelper.model.weight.Weight;
import fithelper.storage.FitHelperStorage;

import fithelper.storage.UserProfileStorage;
import fithelper.storage.WeightRecordsStorage;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FITHELPER_FILE_OPS_ERROR_MESSAGE = "Could not save data to fitHelper file: ";
    public static final String USERPROFILE_FILE_OPS_ERROR_MESSAGE = "Could not save data to user profile file: ";
    public static final String WEIGHTRECORDS_FILE_OPS_ERROR_MESSAGE = "Could not save data to weight records file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final FitHelperStorage fitHelperStorage;
    private final UserProfileStorage userProfileStorage;
    private final WeightRecordsStorage weightRecordsStorage;
    private final FitHelperParser fitHelperParser;

    public LogicManager(Model model, FitHelperStorage fitHelperStorage, UserProfileStorage userProfileStorage,
                        WeightRecordsStorage weightRecordsStorage) {
        this.model = model;
        this.fitHelperStorage = fitHelperStorage;
        this.userProfileStorage = userProfileStorage;
        this.weightRecordsStorage = weightRecordsStorage;
        this.fitHelperParser = new FitHelperParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = fitHelperParser.parseCommand(commandText);
        commandResult = command.execute(model);

        // save entry data
        try {
            fitHelperStorage.saveFitHelper(model.getFitHelper());
        } catch (IOException ioe) {
            logger.severe(ioe.getMessage());
            throw new CommandException(FITHELPER_FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        // save user profile data
        try {
            userProfileStorage.saveUserProfile(model.getUserProfile());
        } catch (IOException ioe) {
            logger.severe(ioe.getMessage());
            throw new CommandException(USERPROFILE_FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        // save weight records data
        try {
            weightRecordsStorage.saveWeightRecords(model.getWeightRecords());
        } catch (IOException ioe) {
            logger.severe(ioe.getMessage());
            throw new CommandException(WEIGHTRECORDS_FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return model.getFitHelper();
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        return model.getUserProfile();
    }

    @Override
    public ReadOnlyWeightRecords getWeightRecords() {
        return model.getWeightRecords();
    }

    @Override
    public ObservableList<Weight> getFilteredWeightList() {
        return model.getFilteredWeightList();
    }

    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        return model.getFilteredDiaryList();
    }

    @Override
    public ObservableList<Entry> getFilteredFoodEntryList() {
        return model.getFilteredFoodEntryList();
    }

    @Override
    public ObservableList<Entry> getFilteredSportsEntryList() {
        return model.getFilteredSportsEntryList();
    }

    @Override
    public ObservableList<Entry> getFilteredReminderEntryList() {
        return model.getFilteredReminderEntryList();
    }

    @Override
    public ObservableList<Entry> getFilteredTodayFoodEntryList() {
        return model.getFilteredTodayFoodEntryList();
    }

    @Override
    public ObservableList<Entry> getFilteredTodaySportsEntryList() {
        return model.getFilteredTodaySportsEntryList();
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        return model.getVEvents();
    }

    @Override
    public LocalDate getCalendarDate() {
        return model.getCalendarDate();
    }

    @Override
    public String getCalendarMode() {
        return model.getCalendarMode();
    }

    @Override
    public LocalDate getCalendarShow() {
        return model.getCalendarShow();
    }

    public void setCalendarShow() {
        model.setCalendarShow(null);
    }

}

