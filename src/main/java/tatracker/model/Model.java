package tatracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import tatracker.commons.core.GuiSettings;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /** {@code Predicates} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** {@code Predicates} that always evaluate to true */
    Predicate<Session> PREDICATE_SHOW_ALL_SESSIONS = unused -> true;

    // ======== TaTracker Methods ==============================================

    /**
     * Returns the TaTracker.
     */
    ReadOnlyTaTracker getTaTracker();

    /**
     * Replaces TaTracker data with the data in {@code taTracker}.
     */
    void setTaTracker(ReadOnlyTaTracker taTracker);

    // ======== User Prefs Methods =============================================

    /**
     * Returns the User Prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces the data in User Prefs with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the TaTracker file path in the User Prefs.
     */
    Path getTaTrackerFilePath();

    /**
     * Replaces the TaTracker file path in the User Prefs.
     */
    void setTaTrackerFilePath(Path taTrackerFilePath);

    /**
     * Returns the GUI settings in the User Prefs.
     */
    GuiSettings getGuiSettings();

    /**
     * Replaces the GUI settings in the User Prefs.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Sets default settings of the student view.
     */
    void setDefaultStudentViewList();

    // ======== Filter Methods ================================================

    /**
     *Sets the currently used filter under Claim View.
     */
    void setCurrClaimFilter(String module);

    /**
     * Get the currently used filter under Claim View.
     */
    String getCurrClaimFilter();

    /**
     *Sets the currently used filter under Session View.
     */
    void setCurrSessionFilter(String params);

    /**
     *Sets the currently used date filter under Session View.
     */
    void setCurrSessionDateFilter(String params);

    /**
     *Sets the currently used module filter under Session View.
     */
    void setCurrSessionModuleFilter(String params);

    /**
     *Sets the currently used type filter under Session View.
     */
    void setCurrSessionTypeFilter(String params);

    /**
     * Get the currently used filter under Session View.
     */
    String getCurrSessionFilter();

    /**
     * Get the currently used date filter under Session View.
     */
    String getCurrSessionDateFilter();

    /**
     * Get the currently used module filter under Session View.
     */
    String getCurrSessionModuleFilter();

    /**
     * Get the currently used type filter under Session View.
     */
    String getCurrSessionTypeFilter();

    /**
     *Sets the currently used filter under Student View.
     */
    void setCurrStudentFilter(String params);

    /**
     * Get the currently used filter under Student View.
     */
    String getCurrStudentFilter();

    // ======== Session Methods ================================================

    /**
     * Returns true if a given session with the same identity as {@code session}
     * exists in TaTracker.
     */
    boolean hasSession(Session session);

    /**
     * Adds the given session into the TaTracker.
     * @param session session to add, which must not already exist in the TaTracker.
     */
    void addSession(Session session);

    /**
     * Deletes the given session {@code target} from the TaTracker.
     * @param target session to delete, which must exist in the TaTracker.
     */
    void deleteSession(Session target);

    /**
     * Replaces the given session {@code target} in the TaTracker with {@code editedSession}.
     * @param target session to edit, which must exist in the TaTracker.
     * @param editedSession the edited session {@code target}.
     *                      The identity of {@code editedSession} must be the same as {@code target}.
     */
    void setSession(Session target, Session editedSession);

    /** Returns an unmodifiable view of the filtered session list */
    ObservableList<Session> getFilteredSessionList();

    /**
     * Updates the filter of the filtered session list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSessionList(Predicate<Session> predicate);

    // ======== Done Session Methods =================================================

    /**
     * Adds the given session to the list of completed sessions.
     * The session must exist in the ta-tracker.
     */
    void addDoneSession(Session session);

    // ======== Module Methods =================================================

    /**
     * Returns the TaTracker module with the given module identifier.
     */
    Module getModule(String moduleId);

    /** Returns an unmodifiable view of the filtered done session list */
    ObservableList<Session> getFilteredDoneSessionList();

    /**
     * Updates the filter of the filtered done session list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoneSessionList(Predicate<Session> predicate, String moduleCode);

    /**
     * Returns true if a given module with the same identity as {@code moduleCode}
     * exists in TaTracker.
     */
    boolean hasModule(String moduleCode);

    /**
     * Adds the given module into the TaTracker.
     * @param module module to add, which must not already exist in the TaTracker.
     */
    void addModule(Module module);

    /**
     * Deletes the given module {@code target} from the TaTracker.
     * @param target module to delete, which must exist in the TaTracker.
     */
    void deleteModule(Module target);

    /**
     * Sorts the modules by rating in ascending order.
     */
    void sortModulesByRatingAscending();

    /**
     * Sorts the modules by rating in descending order.
     */
    void sortModulesByRatingDescending();

    /**
     * Sorts all the students of all groups in all the modules alphabetically.
     */
    void sortModulesAlphabetically();

    /**
     * Sorts all students of all groups in all the modules by matric number.
     */
    void sortModulesByMatricNumber();


    /**
     * Replaces the given module {@code target} in the TaTracker with {@code editedModule}.
     * @param target module to edit, which must exist in the TaTracker.
     * @param editedModule the edited module {@code target}.
     *                     The identity of {@code editedModule} must be the same as {@code target}.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Shows all modules.
     */
    void showAllModules();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    // ======== Group Methods ==================================================


    /**
     * Returns true if a given group with the same identity as {@code groupCode}
     * exists in a module that is in TaTracker.
     * @param moduleCode module that contains group with the given {@code groupCode}.
     */
    boolean hasGroup(String groupCode, String moduleCode);

    /**
     * Adds the given group into a module that is in TaTracker.
     * @param group group to add, which must not already exist in the TaTracker module.
     * @param targetModule module to add {@code group} into, which must exist in the TaTracker.
     */
    void addGroup(Group group, Module targetModule);

    /**
     * Deletes the given group {@code target} from a module that is in TaTracker.
     * @param target group code whose group to delete, which must exist in the TaTracker module.
     * @param targetModule module code whose module has the group that must be deleted.
     */
    void deleteGroup(String target, String targetModule);

    /**
     * Replaces the given group {@code target} in a TaTracker module with {@code editedGroup}.
     * @param target group to edit, which must exist in the TaTracker module.
     * @param editedGroup the edited group {@code target}.
     *                    The identity of {@code editedGroup} must be the same as {@code target}.
     * @param targetModule module with the group to edit, which must exist in the TaTracker.
     */
    void setGroup(Group target, Group editedGroup, Module targetModule);

    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filtered group list to show the groups in module with the given
     * module code.
     */
    void updateFilteredGroupList(String moduleCode);

    /**
     * Sets the filtered group list to be an empty list.
     */
    void setFilteredGroupList();

    /**
     * Updates the group list to show the groups in the module with the given index.
     */
    void updateGroupList(int index);

    // ======== Student Methods ================================================

    /**
     * Returns true if a student with the same identity as {@code student} exists in the TaTracker.
     */
    boolean hasStudent(Student student);

    /**
     * Returns true if a given student with the given {@code matric}
     * exists in a module group that is in TaTracker.
     * @param targetGroup id of group to check if {@code student} is enrolled in.
     * @param targetModule id of module that contains {@code group}.
     */
    boolean hasStudent(Matric matric, String targetGroup, String targetModule);

    /**
     * Returns the student with the given {@code matric} in the TA-Tracker.
     * This student will be inside the group with the given {@code groupCode},
     * which in turn is inside the module with the given {@code moduleCode}/
     * @param matric of the student
     * @param groupCode group to check if {@code student} is enrolled in.
     * @param moduleCode module that contains {@code group}.
     */
    Student getStudent(Matric matric, String groupCode, String moduleCode);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the ta-tracker.
     */
    void addStudent(Student student);

    /**
     * Adds the given student into a module group that is in TaTracker.
     * @param student student to add, which must not already exist in the TaTracker module group.
     * @param targetGroup group to add {@code student} into, which must exist in the TaTracker module.
     * @param targetModule module to add {@code student} into, which must exist in the TaTracker.
     */
    void addStudent(Student student, String targetGroup, String targetModule);

    /**
     * Deletes the given student.
     * The student must exist in the ta-tracker.
     */
    void deleteStudent(Student target);

    /**
     * Deletes the given student {@code target} from a module group that is in TaTracker.
     * @param target student to delete, which must exist in the TaTracker module group.
     * @param targetGroup group to delete student {@code target} from, which must exist in the TaTracker module.
     * @param targetModule module to delete student {@code target} from, which must exist in the TaTracker.
     */
    void deleteStudent(Student target, String targetGroup, String targetModule);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the ta-tracker.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the tracker.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Replaces the given student {@code target} in a TaTracker module group with {@code editedStudent}.
     * @param target student to edit, which must exist in the TaTracker module group.
     * @param editedStudent the edited student {@code target}.
     *                      The identity of {@code editedStudent} must be the same as {@code target}.
     * @param targetGroup group with the student to edit, which must exist in the TaTracker module.
     * @param targetModule module with the student to edit, which must exist in the TaTracker.
     */
    void setStudent(Student target, Student editedStudent, String targetGroup, String targetModule);

    /**
     * Sets the student list to be of group of index groupIndex in the module of index moduleIndex.
     */
    void updateStudentList(int moduleIndex, int groupIndex);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the currently shown student list to show students of the given group
     * from the given module.
     */
    void updateFilteredStudentList(String groupCode, String moduleCode);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Sets the filtered student list to be an empty list.
     */
    void setFilteredStudentList();

    /**
     * Sets the filtered student list to be an that of given index group in given module.
     */
    void setFilteredStudentList(String moduleCode, int groupIndex);

    /**
     * Sets the pay rate to a integer specified by the user
     * @param rate the new rate
     */
    void setRate(int rate);
}
