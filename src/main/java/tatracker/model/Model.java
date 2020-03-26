package tatracker.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import tatracker.commons.core.GuiSettings;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    // TODO: Remove interface constants (aim for pure interface).

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

    // ======== Module Methods =================================================

    /**
     * Returns the TaTracker module with the given module identifier.
     */
    Module getModule(String moduleId);

    /**
     * Returns true if a given module with the same identity as {@code module}
     * exists in TaTracker.
     */
    boolean hasModule(Module module);

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
     * Replaces the given module {@code target} in the TaTracker with {@code editedModule}.
     * @param target module to edit, which must exist in the TaTracker.
     * @param editedModule the edited module {@code target}.
     *                     The identity of {@code editedModule} must be the same as {@code target}.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    // ======== Group Methods ==================================================

    /**
     * Returns true if a given group with the same identity as {@code group}
     * exists in a module that is in TaTracker.
     * @param targetModule module that contains {@code group}.
     */
    boolean hasGroup(Group group, Module targetModule);

    /**
     * Adds the given group into a module that is in TaTracker.
     * @param group group to add, which must not already exist in the TaTracker module.
     * @param targetModule module to add {@code group} into, which must exist in the TaTracker.
     */
    void addGroup(Group group, Module targetModule);

    /**
     * Deletes the given group {@code target} from a module that is in TaTracker.
     * @param target group to delete, which must exist in the TaTracker module.
     * @param targetModule module to delete group {@code target} from, which must exist in the TaTracker.
     */
    void deleteGroup(Group target, Module targetModule);

    /**
     * Replaces the given group {@code target} in a TaTracker module with {@code editedGroup}.
     * @param target group to edit, which must exist in the TaTracker module.
     * @param editedGroup the edited group {@code target}.
     *                    The identity of {@code editedGroup} must be the same as {@code target}.
     * @param targetModule module with the group to edit, which must exist in the TaTracker.
     */
    void setGroup(Group target, Group editedGroup, Module targetModule);

    // TODO: Group filter methods.

    // ======== Student Methods ================================================

    /**
     * Returns true if a student with the same identity as {@code student} exists in the TaTracker.
     */
    boolean hasStudent(Student student);

    /**
     * Returns true if a given student with the same identity as {@code student}
     * exists in a module group that is in TaTracker.
     * @param targetGroup group to check if {@code student} is enrolled in.
     * @param targetModule module that contains {@code group}.
     */
    boolean hasStudent(Student student, Group targetGroup, Module targetModule);

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
    void addStudent(Student student, Group targetGroup, Module targetModule);

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
    void deleteStudent(Student target, Group targetGroup, Module targetModule);

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
    void setStudent(Student target, Student editedStudent, Group targetGroup, Module targetModule);

    // TODO: Student filter methods. Javadoc comments should mention students are inside group -> inside module

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
}
