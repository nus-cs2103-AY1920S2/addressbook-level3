package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ModManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.UserInputHistory;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModManagerParser modManagerParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        modManagerParser = new ModManagerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        UserInputHistory.saveInput(commandText);

        CommandResult commandResult;
        Command command = modManagerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveModManager(model.getModManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyModManager getModManager() {
        return model.getModManager();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Facilitator> getFilteredFacilitatorList() {
        return model.getFilteredFacilitatorList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public List<Lesson> getLessons() {
        return model.getLessons();
    }

    @Override
    public Optional<Module> getModule() {
        return model.getModule();
    }

    @Override
    public ObservableList<Facilitator> getFacilitatorListForModule() {
        return model.getFacilitatorListForModule();
    }

    @Override
    public ObservableList<Task> getTaskListForModule() {
        return model.getTaskListForModule();
    }

    @Override
    public ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode) {
        return model.getLessonListForModule(moduleCode);
    }

    @Override
    public Calendar getCalendar() {
        return model.getCalendar();
    }

    @Override
    public Path getModManagerFilePath() {
        return model.getModManagerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

}
