package tatracker.model;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Represents the in-memory model of the ta-tracker data.
 */
public class ModelManager implements Model {
    //@@author aakanksha-rai

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int FIRST_GROUP_INDEX = 0;
    private static final int FIRST_MODULE_INDEX = 0;

    private final TaTracker taTracker;
    private final UserPrefs userPrefs;

    private final FilteredList<Module> filteredModules;

    //@@author Chuayijing

    private final FilteredList<Session> filteredSessions;

    //@@author fatin99

    private final FilteredList<Session> filteredDoneSessions;

    //@@author

    /**
     * Initializes a ModelManager with the given taTracker and userPrefs.
     */
    public ModelManager(ReadOnlyTaTracker taTracker, ReadOnlyUserPrefs userPrefs) {
        super();
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

    //@@author aakanksha-rai
    @Override
    public void setDefaultStudentViewList() {
        if (this.getFilteredModuleList().isEmpty()) {
            this.setFilteredGroupList();
            this.setFilteredStudentList();
            taTracker.setCurrentlyShownGroup(null);
            taTracker.setCurrentlyShownModule(null);
        } else {
            this.updateGroupList(FIRST_MODULE_INDEX);
            taTracker.setCurrentlyShownModule(taTracker.getModule(FIRST_MODULE_INDEX));
            if (this.getFilteredGroupList().isEmpty()) {
                taTracker.setCurrentlyShownGroup(null);
                this.setFilteredStudentList();
            } else {
                taTracker.setCurrentlyShownGroup(taTracker.getModule(FIRST_MODULE_INDEX).get(FIRST_GROUP_INDEX));
                this.updateStudentList(FIRST_GROUP_INDEX, FIRST_MODULE_INDEX);
            }
        }
    }

    // ======== Filter Methods ==============================================

    //@@author Chuayijing
    @Override
    public void setCurrClaimFilter(String module) {
        requireAllNonNull(module);
        logger.info(String.format("Claims are filtered by %s", module));
        taTracker.setCurrClaimFilter(module);
    }

    //@@author Chuayijing
    @Override
    public String getCurrClaimFilter() {
        return taTracker.getCurrClaimFilter();
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionFilter(String params) {
        requireAllNonNull(params);
        logger.info(String.format("Claims are filtered by %s", params));
        taTracker.setCurrSessionFilter(params);
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionDateFilter(String params) {
        requireAllNonNull(params);
        logger.info(String.format("Sessions are filtered by %s", params));
        taTracker.setCurrSessionDateFilter(params);
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionModuleFilter(String params) {
        requireAllNonNull(params);
        logger.info(String.format("Sessions are filtered by %s", params));
        taTracker.setCurrSessionModuleFilter(params);
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionTypeFilter(String params) {
        requireAllNonNull(params);
        taTracker.setCurrSessionTypeFilter(params);
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionFilter() {
        return taTracker.getCurrSessionFilter();
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionDateFilter() {
        return taTracker.getCurrSessionDateFilter();
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionModuleFilter() {
        return taTracker.getCurrSessionModuleFilter();
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionTypeFilter() {
        return taTracker.getCurrSessionTypeFilter();
    }

    //@@author Chuayijing
    @Override
    public void setCurrStudentFilter(String params) {
        requireAllNonNull(params);
        taTracker.setCurrStudentFilter(params);
    }

    //@@author Chuayijing
    @Override
    public String getCurrStudentFilter() {
        return taTracker.getCurrStudentFilter();
    }

    // ======== Session Methods ================================================

    //@@author Chuayijing
    @Override
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return taTracker.hasSession(session);
    }

    //@@author Chuayijing
    @Override
    public void addSession(Session session) {
        taTracker.addSession(session);
        logger.info(String.format("Session added is %s", session));
        updateFilteredSessionList(PREDICATE_SHOW_ALL_SESSIONS);
    }

    //@@author Chuayijing
    @Override
    public void deleteSession(Session target) {
        logger.info(String.format("Session deleted is %s", target));
        taTracker.removeSession(target);
    }

    //@@author Chuayijing
    @Override
    public void setSession(Session target, Session editedSession) {
        requireAllNonNull(target, editedSession);
        logger.info(String.format("Session %s is changed to %s", target, editedSession));
        taTracker.setSession(target, editedSession);
    }

    //@@author Chuayijing
    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    //@@author Chuayijing
    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
    }

    // ======== Done Session Methods =================================================

    //@@author fatin99
    @Override
    public void addDoneSession(Session session) {
        logger.info(String.format("Session marked as done is %s", session));
        taTracker.addDoneSession(session);
        updateFilteredDoneSessionList(PREDICATE_SHOW_ALL_SESSIONS, "");
    }

    //@@author fatin99

    /**
     * Returns an unmodifiable view of the list of {@code Session} backed by the internal list of
     * {@code versionedTaTracker}
     */
    @Override
    public ObservableList<Session> getFilteredDoneSessionList() {
        return filteredDoneSessions;
    }

    //@@author fatin99

    @Override
    public void updateFilteredDoneSessionList(Predicate<Session> predicate, String moduleCode) {
        requireNonNull(predicate);
        taTracker.setCurrentlyShownModuleClaim(moduleCode);
        logger.info("Done sessions are filtered by " + moduleCode);
        filteredDoneSessions.setPredicate(predicate);
    }

    //@@author fatin99

    @Override
    public void setRate (int rate) {
        taTracker.setRate(rate);
    }

    // ======== Module Methods =================================================

    //@@author aakanksha-rai
    public Module getModule(String code) {
        requireNonNull(code);
        return taTracker.getModule(code);
    }

    //@@author aakanksha-rai
    @Override
    public boolean hasModule(String moduleCode) {
        requireNonNull(moduleCode);
        return taTracker.hasModule(moduleCode);
    }

    //@@author aakanksha-rai
    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        logger.info(String.format("Module added is %s", module));
        taTracker.addModule(module);
    }

    //@@author aakanksha-rai
    @Override
    public void deleteModule(Module module) {
        requireNonNull(module);
        logger.info(String.format("Module deleted is %s", module));
        taTracker.deleteModule(module);
    }

    //@@author aakanksha-rai
    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);
        logger.info(String.format("Module %s is changed to %s", target, editedModule));
        taTracker.setModule(target, editedModule);
    }

    //@@author aakanksha-rai
    @Override
    public void sortModulesAlphabetically() {
        logger.info("Modules are sorted alphabetically");
        taTracker.sortModulesAlphabetically();
    }

    //@@author aakanksha-rai
    @Override
    public void sortModulesByRatingAscending() {
        logger.info("Modules are sorted by rating in ascending order");
        taTracker.sortModulesByRatingAscending();
    }

    //@@author aakanksha-rai
    @Override
    public void sortModulesByRatingDescending() {
        logger.info("Modules are sorted by rating in descending order");
        taTracker.sortModulesByRatingDescending();
    }

    //@@author aakanksha-rai
    @Override
    public void sortModulesByMatricNumber() {
        logger.info("Modules are sorted by matric number in ascending order");
        taTracker.sortModulesByMatricNumber();
    }

    //@@author aakanksha-rai
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    //@@author aakanksha-rai
    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //@@author aakanksha-rai
    @Override
    public void showAllModules() {
        logger.info("All modules are shown");
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    // ======== Group Methods ==================================================

    //@@author aakanksha-rai
    @Override
    public boolean hasGroup(String groupCode, String moduleCode) {
        requireNonNull(groupCode, moduleCode);
        return taTracker.hasGroup(groupCode, moduleCode);
    }

    //@@author aakanksha-rai
    @Override
    public void addGroup(Group group, Module targetModule) {
        requireNonNull(group);
        logger.info(String.format("Group %s is added to module %s", group, targetModule));
        taTracker.addGroup(group, targetModule);
    }

    //@@author aakanksha-rai
    @Override
    public void deleteGroup(String target, String targetModule) {
        requireNonNull(target);
        logger.info(String.format("Group %s is deleted from %s", target, targetModule));
        taTracker.removeGroup(new Group(target), new Module(targetModule));
    }

    //@@author aakanksha-rai
    @Override
    public void setGroup(Group target, Group editedGroup, Module targetModule) {
        requireAllNonNull(target, editedGroup);
        logger.info(String.format("Group %s in module %s is changed to %s",
                target, targetModule, editedGroup));
        taTracker.setGroup(target, editedGroup, targetModule);
    }

    //@@author aakanksha-rai
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return taTracker.getCurrentlyShownGroupList();
    }

    //@@author aakanksha-rai
    @Override
    public void updateFilteredGroupList(String moduleCode) {
        logger.info(String.format("Group list being shown is of module %s", moduleCode));
        taTracker.updateCurrentlyShownGroups(moduleCode);
    }

    //@@author aakanksha-rai
    @Override
    public void setFilteredGroupList() {
        logger.info("Empty grouplist is shown");
        taTracker.setCurrentlyShownGroups(new ArrayList<>());
    }

    //@@author aakanksha-rai
    @Override
    public void updateGroupList(int moduleIndex) {
        logger.info(String.format("Groups from module %d is showns", moduleIndex));
        taTracker.setCurrentlyShownGroups(moduleIndex);
    }

    // ======== Student Methods ================================================

    //@@author

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return taTracker.hasStudent(student);
    }

    //@@author aakanksha-rai

    @Override
    public boolean hasStudent(Matric matric, String targetGroup, String targetModule) {
        requireNonNull(matric);
        return taTracker.hasStudent(matric, targetGroup, targetModule);
    }

    //@@author aakanksha-rai

    @Override
    public Student getStudent(Matric matric, String groupCode, String moduleCode) {
        return taTracker.getStudent(matric, groupCode, moduleCode);
    }

    //@@author

    @Override
    public void addStudent(Student student) {
        logger.info(String.format("Student added is %s", student));
        taTracker.addStudent(student);
    }

    //@@author fatin99

    @Override
    public void addStudent(Student student, String targetGroup, String targetModule) {
        requireNonNull(student);
        logger.info(String.format("Student added is %s into group %s of module %s",
                student, targetGroup, targetModule));
        taTracker.addStudent(student, targetGroup, targetModule);
    }

    //@@author

    @Override
    public void deleteStudent(Student target) {
        taTracker.removeStudent(target);
    }

    //@@author fatin99

    @Override
    public void deleteStudent(Student target, String targetGroup, String targetModule) {
        requireNonNull(target);
        logger.info(String.format("Student deleted is %s from group %s of module %s",
                target, targetGroup, targetModule));
        taTracker.deleteStudent(target, targetGroup, targetModule);
    }

    //@@author

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        taTracker.setStudent(target, editedStudent);
    }

    //@@author potatocombat

    @Override
    public void setStudent(Student target, Student editedStudent, String targetGroup, String targetModule) {
        requireAllNonNull(target, editedStudent);
        logger.info(String.format("Student edited is %s to %s from group %s of module %s",
                target, editedStudent, targetGroup, targetModule));
        taTracker.setStudent(target, editedStudent, targetGroup, targetModule);
    }

    //@@author aakanksha-rai
    @Override
    public void updateStudentList(int moduleIndex, int groupIndex) {
        logger.info(String.format("Students are from module of index %d and group index %d",
                moduleIndex, groupIndex));
        taTracker.setCurrentlyShownStudents(moduleIndex, groupIndex);
    }

    //@@author aakanksha-rai
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return taTracker.getCurrentlyShownStudentList();
    }

    //@@author aakanksha-rai
    @Override
    public void setFilteredStudentList() {
        logger.info("Empty student list is shown.");
        taTracker.setCurrentlyShownStudents(new ArrayList<>());
    }

    //@@author aakanksha-rai
    @Override
    public void setFilteredStudentList(String moduleCode, int groupIndex) {
        logger.info(String.format("Students of group index %d from module %s are showmn",
                groupIndex, moduleCode));
        taTracker.setCurrentlyShownStudents(moduleCode, groupIndex);
    }

    //@@author aakanksha-rai
    @Override
    public void updateFilteredStudentList(String groupCode, String moduleCode) {
        logger.info(String.format("Students are shown of group %s of module %s",
                groupCode, moduleCode));
        taTracker.updateCurrentlyShownStudents(groupCode, moduleCode);
    }

    //@@author aakanksha-rai
    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
    }

    // ======== Others Methods =================================================

    //@@author
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
