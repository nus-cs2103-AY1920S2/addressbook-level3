package seedu.address.model;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.grades.Grade;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.student.Enrollment;
import seedu.address.model.student.Student;
import seedu.address.model.student.TimeTable;
import seedu.address.model.time.StudentSemester;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the Planner
     */
    ReadOnlyPlanner getPlanner();

    ObservableList<Student> getStudentList();

    boolean hasStudent(Student student);

    Student getActiveStudent();

    void setActiveStudent(Student editedStudent);

    void activateStudent(Student student);

    void addStudent(Student student);

    void removeStudent(Student student);

    ObservableList<ModuleCode> getEnrolledModuleCodes();

    boolean hasEnrollment(ModuleCode moduleCode);

    void addEnrollment(Enrollment enrollment);

    void removeEnrollment(ModuleCode moduleCode);

    void activateSemester(StudentSemester studentSemester);

    TimeTable getActiveTimeTable();

    void addSemesterTimeTable(StudentSemester studentSemester);

    void removeSemesterTimeTable(StudentSemester studentSemester);

    Optional<Grade> getModuleGrade(ModuleCode moduleCode);

    void setModuleGrade(ModuleCode moduleCode, Grade grade);

    void setPlanner(Planner planner);
}
