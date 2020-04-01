package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.grades.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.student.Enrollment;
import seedu.address.model.student.Student;
import seedu.address.model.student.TimeTable;
import seedu.address.model.time.StudentSemester;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Planner planner;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given planner and userPrefs.
     */
    public ModelManager(ReadOnlyPlanner planner, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(planner, userPrefs);

        logger.fine("Initializing with planner: " + planner + " and user prefs " + userPrefs);
        this.planner = new Planner(planner);
        this.userPrefs = new UserPrefs(userPrefs);

        requireAllNonNull(planner);
        logger.fine("Initializing with planner: " + planner + " and user prefs " + userPrefs);
        // this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager(ReadOnlyPlanner planner) {
        this(planner, new UserPrefs());
    }

    public ModelManager() {
        this(new Planner(), new UserPrefs());
    }
    //=========== UserPrefs ==================================================================================

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
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyPlanner getPlanner() {
        return planner;
    }

    public void setPlanner(Planner planner) {
        this.planner.resetData(planner);
    }

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
        return planner.equals(other.planner)
                && userPrefs.equals(other.userPrefs);
    }

    public ObservableList<Student> getStudentList() {
        return planner.getStudentList();
    }

    public void addModule(Module module) {
        planner.addModule(module);
    }

    public ObservableList<Module> getFilteredModuleList() {
        return planner.getModuleList();
    }

    public boolean hasModule(Module module) {
        if (module == null) {
            throw new NullPointerException();
        }
        return planner.getModuleList().contains(module);
    }

    public boolean hasStudent(Student student) {
        return planner.hasStudent(student);
    }

    public Student getActiveStudent() {
        return planner.getActiveStudent();
    }

    public void setActiveStudent(Student editedStudent) {
        planner.setActiveStudent(editedStudent);
    }

    public void activateStudent(Student student) {
        planner.activateStudent(student);
    }

    public void addStudent(Student student) {
        requireAllNonNull(student);
        planner.addStudent(student);
    }

    public void removeStudent(Student student) {
        requireAllNonNull(student);
        planner.removeStudent(student);
    }

    public ObservableList<ModuleCode> getEnrolledModuleCodes() {
        return planner.getActiveModuleCodes();
    }

    // TODO: replace with `TimeTable` and `Enrollment`
    public boolean hasEnrollment(ModuleCode moduleCode) {
        return planner.hasEnrollment(moduleCode);
    }

    public void addEnrollment(Enrollment enrollment) {
        planner.addEnrollment(enrollment);
    }

    public void removeEnrollment(ModuleCode moduleCode) {
        planner.removeEnrollment(moduleCode);
    }

    public void activateSemester(StudentSemester studentSemester) {
        planner.activateSemester(studentSemester);
    }

    public TimeTable getActiveTimeTable() {
        return planner.getActiveTimeTable();
    }

    public void addSemesterTimeTable(StudentSemester studentSemester) {
        planner.addSemesterTimeTable(studentSemester);
    }

    public void removeSemesterTimeTable(StudentSemester studentSemester) {
        planner.removeSemesterTimeTable(studentSemester);
    }

    public Optional<Grade> getModuleGrade(ModuleCode moduleCode) {
        return planner.getModuleGrade(moduleCode);
    }

    public void setModuleGrade(ModuleCode moduleCode, Grade grade) {
        planner.setModuleGrade(moduleCode, grade);
    }

    public Path setPlannerFilePath(Path path) {
        if (path == null) {
            throw new NullPointerException();
        }
        return path;
    }
}
