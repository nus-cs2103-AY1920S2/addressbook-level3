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
import tatracker.model.student.Matric;
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
    public void setDefaultStudentViewList() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrClaimFilter(String module) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrClaimFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionFilter(String params) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionDateFilter(String params) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionDateFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionModuleFilter(String params) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionModuleFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrSessionTypeFilter(String params) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrSessionTypeFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setCurrStudentFilter(String params) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public String getCurrStudentFilter() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public boolean hasSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void addSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void deleteSession(Session target) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public void setSession(Session target, Session editedSession) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
    @Override
    public ObservableList<Session> getFilteredSessionList() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chuayijing
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
    public void updateFilteredDoneSessionList(Predicate<Session> predicate, String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Module getModule(String moduleId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(String moduleCode) {
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
    public void showAllModules() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroup(String groupCode, String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public void addGroup(Group group, Module targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroup(String target, String targetModule) {
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
    public boolean hasStudent(Matric student, String targetGroup, String targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Student getStudent(Matric matric, String groupCode, String moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student, String targetGroup, String targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target, String targetGroup, String targetModule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent, String targetGroup, String targetModule) {
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
    public void sortModulesByMatricNumber() {
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
    public void updateFilteredStudentList(Predicate<Student> predicate) {
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

    @Override
    public void setRate(int rate) {
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

        /**
         * Checks if the session contains the module specified.
         */
        public boolean hasModule(String moduleCode) {
            requireNonNull(moduleCode);
            return this.session.getModuleCode().equals(session.getModuleCode());
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
        private Module module;

        public ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(String moduleCode) {
            requireNonNull(moduleCode);
            return moduleCode.equals(module.getIdentifier());
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
        public boolean hasModule(String moduleCode) {
            requireNonNull(moduleCode);
            return true;
        }

        @Override
        public boolean hasGroup(String groupCode, String module) {
            requireNonNull(groupCode);
            return true;
        }

        @Override
        public boolean hasStudent(Matric matric, String group, String module) {
            requireNonNull(matric);
            return matric.equals(student.getMatric());
        }
    }

    //@@author Chuayijing
    /**
     * A Model stub that always accept the session being added.
     */
    public static class ModelStubAcceptingSessionAdded extends ModelStub {
        public final ArrayList<Session> sessionsAdded = new ArrayList<>();
        public final ArrayList<String> moduleIds = new ArrayList<>();

        @Override
        public boolean hasModule(String moduleId) {
            requireNonNull(moduleId);
            return moduleIds.contains(moduleId);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            moduleIds.add(module.getIdentifier());
        }

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

    //@@author
    /**
     * A Model stub that always accept the student being added.
     */
    public static class ModelStubAcceptingStudentAdded extends ModelStub {
        public final ArrayList<Student> studentsAdded = new ArrayList<>();
        private String moduleShown = "";
        private String groupShown = "";

        @Override
        public boolean hasModule(String moduleCode) {
            requireNonNull(moduleCode);
            return true;
        }

        @Override
        public boolean hasGroup(String groupCode, String module) {
            requireNonNull(groupCode);
            return true;
        }

        @Override
        public boolean hasStudent(Matric matric, String group, String module) {
            requireNonNull(matric);
            return studentsAdded.stream().map(Student::getMatric).anyMatch(matric::equals);
        }

        @Override
        public void addStudent(Student student, String group, String module) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyTaTracker getTaTracker() {
            return new TaTracker();
        }

        @Override
        public void updateFilteredGroupList(String moduleCode) {
            moduleShown = moduleCode;
        }

        @Override
        public void updateFilteredStudentList(String groupCode, String moduleCode) {
            moduleShown = moduleCode;
            groupShown = groupCode;
        }

    }

    /**
     * A Model stub that always accept the module being added.
     */
    public static class ModelStubAcceptingModuleAdded extends ModelStub {
        public final ArrayList<Module> modulesAdded = new ArrayList<>();
        private String moduleShown = "";
        private String groupShown = "";

        @Override
        public boolean hasModule(String moduleCode) {
            requireNonNull(moduleCode);
            return modulesAdded.stream().map(Module::getIdentifier).anyMatch(moduleCode::equals);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public void deleteModule(Module module) {
            requireNonNull(module);
            modulesAdded.remove(module);
        }

        @Override
        public void updateFilteredGroupList(String moduleCode) {
            moduleShown = moduleCode;
        }

        @Override
        public void setFilteredStudentList() {
            groupShown = "";
        }

        @Override
        public void setDefaultStudentViewList() {
            if (modulesAdded.size() == 0) {
                moduleShown = "";
                groupShown = "";
            } else {
                moduleShown = modulesAdded.get(0).getIdentifier();
                if (modulesAdded.get(0).getUniqueGroupList().size() == 0) {
                    groupShown = "";
                } else {
                    groupShown = modulesAdded.get(0).get(0).getIdentifier();
                }
            }
        }

        @Override
        public Module getModule(String moduleId) {
            for (int i = 0; i < modulesAdded.size(); ++i) {
                if (moduleId.equals(modulesAdded.get(i).getIdentifier())) {
                    return modulesAdded.get(i);
                }
            }
            return null;
        }

        @Override
        public ReadOnlyTaTracker getTaTracker() {
            return new TaTracker();
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            return null;
        }

        @Override
        public void updateFilteredStudentList(String groupCode, String moduleCode) {
            moduleShown = moduleCode;
            groupShown = groupCode;
        }

        @Override
        public void showAllModules() {
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            requireNonNull(predicate);
        }

        @Override
        public boolean hasGroup(String groupCode, String moduleCode) {
            return getModule(moduleCode).hasGroup(new Group(groupCode));
        }

        @Override
        public void deleteGroup(String target, String targetModule) {
            Module module = getModule(targetModule);
            module.deleteGroup(new Group(target));
        }
    }
}
