package tatracker.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.group.UniqueGroupList;
import tatracker.model.module.Module;
import tatracker.model.module.UniqueModuleList;
import tatracker.model.session.Session;
import tatracker.model.session.UniqueDoneSessionList;
import tatracker.model.session.UniqueSessionList;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;

/**
 * Wraps all data at the ta-tracker level
 * Duplicates are not allowed (by .isSameSession comparison)
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class TaTracker implements ReadOnlyTaTracker {

    private final UniqueStudentList students;
    private final UniqueSessionList sessions;
    private final UniqueDoneSessionList doneSessions;
    private final UniqueModuleList modules;
    private final UniqueGroupList groups;

    public TaTracker() {
        students = new UniqueStudentList();
        sessions = new UniqueSessionList();
        doneSessions = new UniqueDoneSessionList();
        modules = new UniqueModuleList();
        groups = new UniqueGroupList();
    }

    /**
     * Creates a TaTracker using the Students in the {@code toBeCopied}
     */
    public TaTracker(ReadOnlyTaTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    /**
     * Replaces the contents of the modules list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the donesession list with {@code donesessions}.
     * {@code donesessions} must not contain duplicate donesessions.
     */
    public void setDoneSessions(List<Session> donesessions) {
        this.doneSessions.setSessions(donesessions);
    }

    /**
     * Resets the existing data of this {@code TaTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyTaTracker newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setSessions(newData.getSessionList());
        setDoneSessions(newData.getDoneSessionList());
        setModules(newData.getModuleList());
        setGroups(newData.getGroupList());
    }

    //// session-level operations

    /**
     * Returns true if a session with the same identity as {@code session} exists in the ta-tracker.
     */
    public boolean hasSession(Session session) {
        requireNonNull(session);
        return sessions.contains(session);
    }

    /**
     * Adds a session to the ta-tracker.
     * The session must not already exist in the ta-tracker.
     */
    public void addSession(Session s) {
        sessions.add(s);
    }

    /**
     * Returns true if a session with the same identity as {@code session} exists in the ta-tracker.
     * Removes {@code session} from this {@code TaTracker}.
     * {@code session} must exist in the ta-tracker.
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * Replaces the given session {@code target} in the list with {@code editedSession}.
     * {@code target} must exist in the ta-tracker.
     * The session identity of {@code editedSession} must not be the same as another
     * existing session in the ta-tracker.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(editedSession);

        sessions.setSession(target, editedSession);
    }

    /**
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setSessions(sessions);
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    public void addDoneSession(Session s) {
        doneSessions.add(s);
    }

    @Override
    public ObservableList<Session> getDoneSessionList() {
        return doneSessions.asUnmodifiableObservableList();
    }

    /**
     * Returns module from TATracker.
     */
    public Module getModule(String moduleId) {
        return modules.getModule(moduleId);
    }

    /**
     * Returns true if a module with the same module code exists in the TATracker.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the TATracker.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Removes module with same module code from TA-Tracker.
     */
    public void deleteModule(Module module) {
        for (int i = 0; i < sessions.size(); ++i) {
            Session session = sessions.get(i);
            if (session.getModuleCode().equals(module.getIdentifier())) {
                sessions.remove(session);
            }
        }
        modules.remove(module);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the ta-tracker.
     * The module identity of {@code editedModule} must not be the same as another existing module in the tracker.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    /**
     * Adds a group to the TATracker.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the ta-tracker.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the ta-tracker.
     * The student must not already exist in the ta-tracker.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the ta-tracker.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the tracker.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaTracker // instanceof handles nulls
                && students.equals(((TaTracker) other).students));
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }
}
