package tatracker.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import tatracker.commons.core.GuiSettings;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public ReadOnlyTaTracker getTaTracker() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTaTracker(ReadOnlyTaTracker taTracker) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getTaTrackerFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTaTrackerFilePath(Path taTrackerFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSession(Session target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSession(Session target, Session editedSession) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDoneSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Session> getFilteredDoneSessionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredDoneSessionList(Predicate<Session> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Module getModule(String moduleId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModule(Module target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortModulesByRatingAscending() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortModulesByRatingDescending() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroup(Group group, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public void addGroup(Group group, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroup(Group target, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGroup(Group target, Group editedGroup, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredGroupList(String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateGroupList(int n) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student, Group targetGroup, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student, Group targetGroup, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target, Group targetGroup, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent, Group targetGroup, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateStudentList(int i, int j) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortModulesAlphabetically() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(String groupCode, String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFilteredStudentList(String moduleCode, int n) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * A Model stub that contains a single session.
     */
    public static class ModelStubWithSession extends ModelStub {
        private final Session session;

        public ModelStubWithSession(Session session) {
            requireNonNull(session);
            this.session = session;
        }

        @Override
        public boolean hasSession(Session session) {
            requireNonNull(session);
            return this.session.isSameSession(session);
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    public static class ModelStubWithModule extends ModelStub {
        private final Module module;

        public ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.equals(module);
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    public static class ModelStubWithStudent extends ModelStub {
        private final Student student;

        public ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return true;
        }

        @Override
        public boolean hasGroup(Group group, Module module) {
            requireNonNull(group);
            return true;
        }

        @Override
        public boolean hasStudent(Student student, Group group, Module module) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the session being added.
     */
    public static class ModelStubAcceptingSessionAdded extends ModelStub {
        public final ArrayList<Session> sessionsAdded = new ArrayList<>();

        @Override
        public boolean hasSession(Session session) {
            requireNonNull(session);
            return sessionsAdded.stream().anyMatch(session::isSameSession);
        }

        @Override
        public void addSession(Session session) {
            requireNonNull(session);
            sessionsAdded.add(session);
        }

        @Override
        public ReadOnlyTaTracker getTaTracker() {
            return new TaTracker();
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    public static class ModelStubAcceptingStudentAdded extends ModelStub {
        public final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return true;
        }

        @Override
        public boolean hasGroup(Group group, Module module) {
            requireNonNull(group);
            return true;
        }

        @Override
        public boolean hasStudent(Student student, Group group, Module module) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student, Group group, Module module) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyTaTracker getTaTracker() {
            return new TaTracker();
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    public static class ModelStubAcceptingModuleAdded extends ModelStub {
        public final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::equals);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyTaTracker getTaTracker() {
            return new TaTracker();
        }
    }
}
