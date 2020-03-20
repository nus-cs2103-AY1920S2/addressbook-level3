package tatracker.model;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaTracker taTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Session> filteredSessions;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given taTracker and userPrefs.
     */
    public ModelManager(ReadOnlyTaTracker addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.taTracker = new TaTracker(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.taTracker.getStudentList());
        filteredSessions = new FilteredList<>(this.taTracker.getSessionList());
        filteredModules = new FilteredList<>(this.taTracker.getModuleList());
    }

    public ModelManager() {
        this(new TaTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== TaTracker ================================================================================

    @Override
    public void setTaTracker(ReadOnlyTaTracker taTracker) {
        this.taTracker.resetData(taTracker);
    }

    @Override
    public ReadOnlyTaTracker getTaTracker() {
        return taTracker;
    }

    //=========== Person List Methods ================================================================================

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return taTracker.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        taTracker.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        taTracker.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent);
    }


    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return taTracker.hasModule(module);
    }

    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        taTracker.addModule(module);
    }


    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        taTracker.setModule(target, editedModule);
    }

    @Override
    public void deleteModule(Module module) {
        requireNonNull(module);
        taTracker.deleteModule(module);
    }

    @Override
    public Module getModule(Module module) {
        requireNonNull(module);
        return taTracker.getModule(module);
    }

    @Override
    public Module getModule(String code) {
        requireNonNull(code);
        Module module = new Module(code, null);
        return taTracker.getModule(module);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Session List Methods ================================================================================

    @Override
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return taTracker.hasSession(session);
    }

    @Override
    public void addSession(Session session) {
        taTracker.addSession(session);
        updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
    }

    @Override
    public void deleteSession(Session target) {
        taTracker.removeSession(target);
    }

    @Override
    public void setSession(Session target, Session editedSession) {
        requireAllNonNull(target, editedSession);

        taTracker.setSession(target, editedSession);
    }

    //=========== Filtered Session List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Session} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
    }

    //=========== Others =============================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return taTracker.equals(other.taTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedAddressBook}
     */

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }
}
