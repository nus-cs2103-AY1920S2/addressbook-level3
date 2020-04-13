package tatracker.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.TaTrackerParser;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.Model;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;
import tatracker.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TaTrackerParser taTrackerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.taTrackerParser = new TaTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = taTrackerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTaTracker(model.getTaTracker());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTaTracker getTaTracker() {
        return model.getTaTracker();
    }

    @Override
    public Path getTaTrackerFilePath() {
        return model.getTaTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return model.getFilteredSessionList();
    }

    @Override
    public ObservableList<Session> getFilteredDoneSessionList() {
        return model.getFilteredDoneSessionList();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public String getCurrSessionDateFilter() {
        return model.getCurrSessionDateFilter();
    }

    @Override
    public String getCurrSessionModuleFilter() {
        return model.getCurrSessionModuleFilter();
    }

    @Override
    public String getCurrSessionTypeFilter() {
        return model.getCurrSessionTypeFilter();
    }

}
