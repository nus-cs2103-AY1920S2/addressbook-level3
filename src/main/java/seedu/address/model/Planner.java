package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.grades.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleDataImporter;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.student.Enrollment;
import seedu.address.model.student.Student;
import seedu.address.model.student.TimeTable;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.model.time.StudentSemester;

/**
 * Wraps all data at the planner level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Planner implements ReadOnlyPlanner {

    /**
     * The list of available modules in NUS.
     */
    protected static UniqueModuleList modules = new UniqueModuleList();
    /**
     * The current student that the user can immediately modify.
     * `activeStudent` must be an element of `students`, i.e. `students.contains(activeStudent)` is `true`
     */
    protected int activeStudentIndex = -1;
    protected StudentSemester activeSemester;
    /**
     * The list of students created by the user.
     */
    protected UniqueStudentList students; //TOOD: use list of students in storage


    public Planner(boolean loadModules) {
        students = new UniqueStudentList();
        if (loadModules) {
            loadModules();
        }
    }

    /**
     * Creates an Planner using the UniqueStudentList in the {@code toBeCopied}.
     */
    public Planner() {
        students = new UniqueStudentList();
        loadModules();
    }

    public Planner(ReadOnlyPlanner planner) {
        this();
        resetData(planner);
    }

    private void loadModules() {
        if (modules.isEmpty()) {
            System.out.println("Loading modules. This might take awhile...");
            List<Module> modulesToImport = ModuleDataImporter.run();
            System.out.println("Done!");
            if (modulesToImport != null) {
                modulesToImport.forEach(x -> modules.add(x));
            }
        }
    }

    public UniqueModuleList getModules() {
        return modules;
    }


    public boolean addStudent(Student student) {
        students.add(student);
        return true;
    }

    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    public void resetActiveStudent(Student target) {
        if (target == null) {
            activeStudentIndex = -1;
        } else {
            activeStudentIndex = getStudentIndex(target);
        }
    }

    public boolean resetData(ReadOnlyPlanner planner) {
        setStudents(planner.getStudentList());
        resetActiveStudent(planner.getActiveStudent());
        activeSemester = planner.getActiveSemester();
        return true;
    }

    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    public boolean hasModule(Module module) {
        return modules.contains(module);
    }


    public boolean addModule(Module module) {
        modules.add(module);
        return true;
    }

    // TODO: Replace `ModuleCode` with`Enrollment`.
    //      Currently we can query with `ModuleCode` and add `Enrollment`.
    public boolean hasEnrollment(ModuleCode moduleCode) {
        TimeTable timeTable = getActiveTimeTable();
        return timeTable.hasModuleCode(moduleCode);
        //return enrolledModules.contains(moduleCode);
    }

    public Enrollment getEnrollment(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);
        TimeTable timeTable = getActiveTimeTable();
        return timeTable.getEnrollment(moduleCode);
        //return enrolledModules.contains(moduleCode);
    }

    public Optional<Grade> getModuleGrade(ModuleCode moduleCode) {
        Enrollment enrollment = getEnrollment(moduleCode);
        return enrollment.getGrade();
    }

    public void setModuleGrade(ModuleCode moduleCode, Grade grade) {
        Enrollment enrollment = getEnrollment(moduleCode);
        enrollment.setGrade(Optional.of(grade));
    }

    public boolean addEnrollment(Enrollment enrollment) {
        getActiveTimeTable().addEnrollment(enrollment);
        return true;
    }

    public boolean removeEnrollment(ModuleCode moduleCode) {
        getActiveTimeTable().removeModuleCode(moduleCode);
        return true;
    }

    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    public ObservableList<ModuleCode> getEnrolledModulesList() {
        return getActiveStudent().getAllEnrolledModules();
    }

    @Override
    public StudentSemester getActiveSemester() {
        return activeSemester;
    }

    @Override
    public int getActiveStudentIndex() {
        return activeStudentIndex;
    }

    public ObservableList<ModuleCode> getActiveModuleCodes() {
        ObservableList<ModuleCode> moduleCodes = FXCollections.observableArrayList();
        moduleCodes.addAll(getActiveTimeTable().getModuleCodes());
        return moduleCodes;
    }

    public ObservableList<ModuleCode> getAllEnrolledModuleCodes() {
        return getActiveStudent().getAllEnrolledModules();
    }

    public void activateValidStudent() {
        // TODO: handle `activeStudents` being null (e.g. if data file is missing)
        // TODO: handle all students being removed
        activeStudentIndex = -1;
        if (!students.isEmpty()) {
            activeStudentIndex = 0;
        }
    }

    public Student getEqualStudent(Student student) {
        return students.getEqualStudent(student);
    }

    private int getStudentIndex(Student student) {
        return students.indexOf(student);
    }

    private boolean isValidStudentIndex(int index) {
        return 0 <= index && index < students.size();
    }

    private boolean isValidActiveStudentIndex() {
        return isValidStudentIndex(activeStudentIndex);
    }

    public Student getActiveStudent() {
        if (!isValidActiveStudentIndex()) {
            return null;
        } else {
            return students.get(activeStudentIndex);
        }
    }

    /**
     * Replaces the currently active student with the student given by (@code editedStudent).
     *
     * @params editedStudent Student to copy for replacement.
     */
    public void setActiveStudent(Student student) {
        requireAllNonNull(student, getActiveStudent());
        students.setStudent(getActiveStudent(), student);
    }

    public void activateStudent(Student student) {
        if (!students.contains(student)) {
            throw new IllegalArgumentException("Student does not exist in student list");
        }
        activeStudentIndex = students.indexOf(student);
        activeSemester = null;
    }

    public void removeStudent(Student toRemove) {
        // TODO: handle all students being removed
        Student activeStudent = getActiveStudent();
        if (toRemove.equals(activeStudent)) {
            students.remove(toRemove);
            activeStudentIndex = -1;
            activeSemester = null; // TODO: validate existing value first
        } else {
            students.remove(toRemove);
            activeStudentIndex = getStudentIndex(activeStudent);
        }
    }

    public TimeTable getActiveTimeTable() {
        requireAllNonNull(getActiveStudent());

        if (activeSemester == null && !getActiveStudent().getTimeTableMap().isEmpty()) {
            activateValidSemester();
        }

        return getActiveStudent().getTimeTable(activeSemester);
    }

    public void setActiveTimeTable(TimeTable timeTable) {
        requireAllNonNull(getActiveStudent());
        getActiveStudent().setTimeTable(activeSemester, timeTable);
    }

    private void activateValidSemester() {
        if (getActiveStudent() == null) {
            throw new IllegalArgumentException("No active student selected");
        }
        requireAllNonNull(activeStudentIndex);

        // TODO: handle `activeStudents` being null (e.g. if data file is missing)
        // TODO: handle all students being removed
        if (getActiveStudent().getTimeTableMap().isEmpty()) {
            throw new IllegalArgumentException("The active student has no timetables");
        }
        activeSemester = getActiveStudent().getTimeTableMap().keySet().iterator().next();
    }

    public void removeTimeTable(StudentSemester keyToRemove) {
        requireAllNonNull(keyToRemove);
        getActiveStudent().removeTimeTable(keyToRemove);
        keyToRemove = null;
    }

    public void requireActiveStudentNonNull() {
        if (getActiveStudent() == null) {
            throw new IllegalArgumentException("No active student selected");
        }
    }

    public boolean hasSemester(StudentSemester semester) {
        requireActiveStudentNonNull();
        return getActiveStudent().getTimeTableMap().containsKey(semester);
    }

    public void activateSemester(StudentSemester semester) {
        requireActiveStudentNonNull();
        if (!getActiveStudent().getTimeTableMap().containsKey(semester)) {
            throw new IllegalArgumentException("Semester does not exist in timetable list");
        }
        activeSemester = semester;
    }

    public void addSemesterTimeTable(StudentSemester studentSemester) {
        requireActiveStudentNonNull();
        if (hasSemester(studentSemester)) {
            throw new IllegalArgumentException("Semester already exists in timetable list");
        }

        getActiveStudent().setTimeTable(studentSemester, new TimeTable());
    }

    public void removeSemesterTimeTable(StudentSemester studentSemester) {
        requireActiveStudentNonNull();
        if (!hasSemester(studentSemester)) {
            throw new IllegalArgumentException("Semester does not exist in timetable list");
        }

        getActiveStudent().removeTimeTable(studentSemester);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Planner planner = (Planner) o;
        return activeStudentIndex == planner.activeStudentIndex
            && Objects.equals(activeSemester, planner.activeSemester)
            && students.equals(planner.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeStudentIndex, activeSemester, students);
    }
}
