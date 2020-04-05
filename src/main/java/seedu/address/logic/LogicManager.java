package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TeaPetParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventScheduleView;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.ReadOnlyNotes;

import seedu.address.model.student.Student;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TeaPetParser teaPetParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        teaPetParser = new TeaPetParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = teaPetParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveAcademics(model.getAcademics());
            storage.saveAdmin(model.getAdmin());
            storage.saveEvents(model.getEventHistory());
            storage.saveNotesManager(model.getNotesManager());

        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return commandResult;
    }

    // ==================== ADDRESS BOOK START ====================
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }
    // ==================== ADDRESS BOOK END ====================

    // ==================== ACADEMICS START ====================
    @Override
    public ReadOnlyAcademics getAcademics() {
        return model.getAcademics();
    }

    @Override
    public ObservableList<Assessment> getFilteredAcademicsList() {
        return model.getFilteredAcademicsList();
    }

    @Override
    public ObservableList<Assessment> getHomeworkList() {
        return model.getHomeworkList();
    }

    @Override
    public ObservableList<Assessment> getExamList() {
        return model.getExamList();
    }

    @Override
    public Path getAcademicsFilePath() {
        return model.getAcademicsFilePath();
    }
    // ==================== ACADEMICS END ====================

    // ==================== ADMIN START ====================
    @Override
    public ReadOnlyAdmin getAdmin() {
        return model.getAdmin();
    }

    @Override
    public ObservableList<Date> getFilteredDateList() {
        return model.getFilteredDateList();
    }

    @Override
    public Path getAdminFilePath() {
        return model.getAdminFilePath();
    }
    // ==================== ADMIN END ====================

    // ==================== NOTES START ====================
    @Override
    public ReadOnlyNotes getNotesManager() {
        return model.getNotesManager();
    }

    @Override
    public ObservableList<Notes> getFilteredNotesList() {
        return model.getFilteredNotesList();
    }

    @Override
    public Path getNotesManagerFilePath() {
        return model.getNotesFilePath();
    }
    // ==================== NOTES END ====================

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        return model.getVEvents();
    }

    @Override
    public LocalDateTime getEventScheduleLocalDateTime() {
        return model.getEventScheduleLocalDateTime();
    }

    @Override
    public EventScheduleView getEventScheduleView() {
        return model.getEventScheduleView();
    }

}
