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
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * Represents the in-memory model of the ta-tracker data.
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
    public ModelManager(ReadOnlyTaTracker taTracker, ReadOnlyUserPrefs userPrefs) {
        super(); // TODO: Super gets interface constants.
        requireAllNonNull(taTracker, userPrefs);

        logger.fine("Initializing with ta-tracker: " + taTracker + " and user prefs " + userPrefs);

        this.taTracker = new TaTracker(taTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredStudents = new FilteredList<>(this.taTracker.getStudentList());
        this.filteredSessions = new FilteredList<>(this.taTracker.getSessionList());
        this.filteredModules = new FilteredList<>(this.taTracker.getModuleList());
    }

    public ModelManager() {
        this(new TaTracker(), new UserPrefs());
    }

    // ======== TaTracker ======================================================

    @Override
    public ReadOnlyTaTracker getTaTracker() {
        return taTracker;
    }

    @Override
    public void setTaTracker(ReadOnlyTaTracker taTracker) {
        this.taTracker.resetData(taTracker);
    }

    // ======== UserPrefs ======================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public Path getTaTrackerFilePath() {
        return userPrefs.getTaTrackerFilePath();
    }

    @Override
    public void setTaTrackerFilePath(Path taTrackerFilePath) {
        requireNonNull(taTrackerFilePath);
        userPrefs.setTaTrackerFilePath(taTrackerFilePath);
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

    // ======== Session Methods ================================================

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

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
    }

    // ======== Module Methods =================================================

    public Module getModule(String code) {
        requireNonNull(code);
        Module module = new Module(code, null);
        return taTracker.getModule(module);
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
    public void deleteModule(Module module) {
        requireNonNull(module);
        taTracker.deleteModule(module);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);
        taTracker.setModule(target, editedModule);
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    // ======== Group Methods ==================================================

    @Override
    public boolean hasGroup(Group group, Module targetModule) {
        requireNonNull(group);
        throw new UnsupportedOperationException("Method under construction");
        // return taTracker.hasGroup(group);
    }

    @Override
    public void addGroup(Group group, Module targetModule) {
        requireNonNull(group);
        throw new UnsupportedOperationException("Method under construction");
        // taTracker.addGroup(group);
    }

    @Override
    public void deleteGroup(Group target, Module targetModule) {
        requireNonNull(target);
        throw new UnsupportedOperationException("Method under construction");
        // taTracker.removeGroup(target);
    }

    @Override
    public void setGroup(Group target, Group editedGroup, Module targetModule) {
        requireAllNonNull(target, editedGroup);
        throw new UnsupportedOperationException("Method under construction");
        // taTracker.setGroup(target, editedGroup);
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return taTracker.hasStudent(student);
    }

    // ======== Student Methods ================================================

    @Override
    public boolean hasStudent(Student student, Group targetGroup, Module targetModule) {
        requireNonNull(student);
        return taTracker.hasStudent(student);
    }

    @Override
    public void addStudent(Student student) {
        taTracker.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addStudent(Student student, Group targetGroup, Module targetModule) {
        requireNonNull(student);
        taTracker.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void deleteStudent(Student target) {
        taTracker.removeStudent(target);
    }

    @Override
    public void deleteStudent(Student target, Group targetGroup, Module targetModule) {
        requireNonNull(target);
        taTracker.removeStudent(target);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent);
    }

    @Override
    public void setStudent(Student target, Student editedStudent, Group targetGroup, Module targetModule) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent);
    }

    /**
     * TODO: Review filter functions.
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

    // ======== Others Methods =================================================

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
}
