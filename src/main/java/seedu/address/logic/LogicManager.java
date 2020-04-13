package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TAbleParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.mod.Mod;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;
import seedu.address.model.util.CommandHistory;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TAbleParser tableParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        tableParser = new TAbleParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = tableParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveStudentTAble(model.getStudentTAble());
            storage.saveConsults(model.getConsultTAble());
            storage.saveMods(model.getModTAble());
            storage.saveTutorials(model.getTutorialTAble());
            storage.saveReminders(model.getReminderTAble());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        CommandHistory.addInput(commandText);
        return commandResult;
    }

    @Override
    public ReadOnlyStudent getAddressBook() {
        return model.getStudentTAble();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return model.getFilteredTutorialList();
    }

    @Override
    public ObservableList<Consult> getFilteredConsultList() {
        return model.getFilteredConsultList();
    }

    @Override
    public ObservableList<Mod> getFilteredModList() {
        return model.getFilteredModList();
    }

    @Override
    public ObservableList<Mod> getViewedMod() {
        return model.getViewedMod();
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return model.getFilteredReminderList();
    }

    @Override
    public ObservableList<Reminder> getUnFilteredReminderList() {
        return model.getUnFilteredReminderList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
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
