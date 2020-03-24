package tatracker.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import tatracker.model.module.Module;
import tatracker.model.module.UniqueModuleList;
import tatracker.model.session.Session;
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
    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        sessions = new UniqueSessionList();
        modules = new UniqueModuleList();
    }

    public TaTracker() {}

    /**
     * Creates a TaTracker using the Students in the {@code toBeCopied}
     */
    public TaTracker(ReadOnlyTaTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setSessions(sessions);
    }

    /**
     * Resets the existing data of this {@code TaTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyTaTracker newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setSessions(newData.getSessionList());
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
     * Returns true if a session with the same identity as {@code session} exists in the ta-tracker.
     * Removes {@code session} from this {@code TaTracker}.
     * {@code session} must exist in the ta-tracker.
     */
    public void removeSession(Session session) {
        sessions.remove(session);
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
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the ta-tracker.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the tracker.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeStudent(Student key) {
        students.remove(key);
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
     * Removes a module from TATracker.
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
     * Returns module from TATracker.
     */
    public Module getModule(Module module) {
        return modules.getModule(module);
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

    /**
     * Removes {@code key} from this {@code TaTracker}.
     * {@code key} must exist in the ta-tracker.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaTracker // instanceof handles nulls
                && students.equals(((TaTracker) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
