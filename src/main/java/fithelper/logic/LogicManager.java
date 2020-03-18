package fithelper.logic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.logic.parser.FitHelperParser;
import fithelper.model.Model;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;
import fithelper.storage.FitHelperStorage;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final FitHelperStorage storage;
    private final FitHelperParser fitHelperParser;

    public LogicManager(Model model, FitHelperStorage storage) {
        this.model = model;
        this.storage = storage;
        this.fitHelperParser = new FitHelperParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = fitHelperParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFitHelper(model.getFitHelper());
        } catch (IOException ioe) {
            logger.severe(ioe.getMessage());
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return model.getFitHelper();
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
    public ObservableList<Entry> getFilteredTodayFoodEntryList(String dateStr) {
        return model.getFilteredTodayFoodEntryList(dateStr);
    }

    @Override
    public ObservableList<Entry> getFilteredTodaySportsEntryList(String dateStr) {
        return model.getFilteredTodaySportsEntryList(dateStr);
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        return model.getVEvents();
    }

    @Override
    public LocalDateTime getCalendarDate() {
        return model.getCalendarDate();
    }

}

