package tatracker.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the ta-tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int FIRST_GROUP_INDEX = 0;
    private static final int FIRST_MODULE_INDEX = 0;

    private final TaTracker taTracker;
    private final UserPrefs userPrefs;

    private final FilteredList<Session> filteredSessions;
    private final FilteredList<Session> filteredDoneSessions;
    private final FilteredList<Module> filteredModules;

    private long totalHours = 0;
    private int rate;
    private long totalEarnings;

    /**
     * Initializes a ModelManager with the given taTracker and userPrefs.
     */
    public ModelManager(ReadOnlyTaTracker taTracker, ReadOnlyUserPrefs userPrefs) {
        super(); // TODO: Super gets interface constants.
        requireAllNonNull(taTracker, userPrefs);

        logger.fine("Initializing with ta-tracker: " + taTracker + " and user prefs " + userPrefs);

        this.taTracker = new TaTracker(taTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSessions = new FilteredList<>(this.taTracker.getSessionList());
        filteredDoneSessions = new FilteredList<>(this.taTracker.getDoneSessionList());
        filteredModules = new FilteredList<>(this.taTracker.getModuleList());
        this.setDefaultStudentViewList();
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

    @Override
    public void setDefaultStudentViewList() {
        if (this.getFilteredModuleList().isEmpty()) {
            this.setFilteredGroupList();
            this.setFilteredStudentList();
        } else {
            this.updateGroupList(FIRST_MODULE_INDEX);
            if (this.getFilteredGroupList().isEmpty()) {
                this.setFilteredStudentList();
            } else {
                this.updateStudentList(FIRST_GROUP_INDEX, FIRST_MODULE_INDEX);
            }
        }
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

    // ======== Done Session Methods =================================================

    @Override
    public void addDoneSession(Session session) {
        taTracker.addDoneSession(session);
        totalHours += Math.ceil(Duration.between
                (session.getEndDateTime(), session.getStartDateTime())
                .toHours());
        updateFilteredDoneSessionList(PREDICATE_SHOW_ALL_SESSIONS);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Session} backed by the internal list of
     * {@code versionedTaTracker}
     */
    @Override
    public ObservableList<Session> getFilteredDoneSessionList() {
        return filteredDoneSessions;
    }

    @Override
    public void updateFilteredDoneSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredDoneSessions.setPredicate(predicate);
    }

    // ======== Module Methods =================================================

    public Module getModule(String code) {
        requireNonNull(code);
        return taTracker.getModule(code);
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
    public void sortModulesAlphabetically() {
        taTracker.sortModulesAlphabetically();
    }

    @Override
    public void sortModulesByRatingAscending() {
        taTracker.sortModulesByRatingAscending();
    }

    @Override
    public void sortModulesByRatingDescending() {
        taTracker.sortModulesByRatingDescending();
    }

    @Override
    public void sortModulesByMatricNumber() {
        taTracker.sortModulesByMatricNumber();
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
        return taTracker.hasGroup(group, targetModule);
    }


    //=========== Filtered Student List Accessors =============================================================

    @Override
    public void addGroup(Group group, Module targetModule) {
        requireNonNull(group);
        taTracker.addGroup(group, targetModule);
    }

    @Override
    public void deleteGroup(Group target, Module targetModule) {
        requireNonNull(target);
        taTracker.removeGroup(target, targetModule);
    }

    @Override
    public void setGroup(Group target, Group editedGroup, Module targetModule) {
        requireAllNonNull(target, editedGroup);
        taTracker.setGroup(target, editedGroup, targetModule);
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return taTracker.getCurrentlyShownGroupList();
    }

    @Override
    public void updateFilteredGroupList(String moduleCode) {
        taTracker.updateCurrentlyShownGroups(moduleCode);
    }

    @Override
    public void setFilteredGroupList() {
        taTracker.setCurrentlyShownGroups(new ArrayList<Group>());
    }

    @Override
    public void updateGroupList(int moduleIndex) {
        taTracker.setCurrentlyShownGroups(moduleIndex);
    }

    // ======== Student Methods ================================================

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return taTracker.hasStudent(student);
    }

    @Override
    public boolean hasStudent(Student student, Group targetGroup, Module targetModule) {
        requireNonNull(student);
        return taTracker.hasStudent(student, targetGroup, targetModule);
    }

    @Override
    public void addStudent(Student student) {
        taTracker.addStudent(student);
    }

    @Override
    public void addStudent(Student student, Group targetGroup, Module targetModule) {
        requireNonNull(student);
        taTracker.addStudent(student, targetGroup, targetModule);
    }

    @Override
    public void deleteStudent(Student target) {
        taTracker.removeStudent(target);
    }

    @Override
    public void deleteStudent(Student target, Group targetGroup, Module targetModule) {
        requireNonNull(target);
        taTracker.deleteStudent(target, targetGroup, targetModule);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent);
    }

    @Override
    public void setStudent(Student target, Student editedStudent, Group targetGroup, Module targetModule) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent, targetGroup, targetModule);
    }

    @Override
    public void updateStudentList(int moduleIndex, int groupIndex) {
        taTracker.setCurrentlyShownStudents(moduleIndex, groupIndex);
    }

    /**
     * TODO: Review filter functions.
     */

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return taTracker.getCurrentlyShownStudentList();
    }

    @Override
    public void setFilteredStudentList() {
        taTracker.setCurrentlyShownStudents(new ArrayList<Student>());
    }

    @Override
    public void setFilteredStudentList(String moduleCode, int groupIndex) {
        taTracker.setCurrentlyShownStudents(moduleCode, groupIndex);
    }

    @Override
    public void updateFilteredStudentList(String groupCode, String moduleCode) {
        taTracker.updateCurrentlyShownStudents(groupCode, moduleCode);
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
                && filteredSessions.equals(other.filteredSessions)
                && filteredModules.equals(other.filteredModules);
    }
}
