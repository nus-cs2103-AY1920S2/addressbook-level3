package seedu.planner.model.student;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.planner.model.module.ModuleCode;
import seedu.planner.model.module.UniqueEnrollmentList;
import seedu.planner.model.student.exceptions.EnrollmentNotFoundException;

//@@author thetruevincentchow

/**
 * Represents a timetable for a semester.
 * A {@code TimeTable} stores {@code Enrollment}s, where each enrollment must have a different module code.
 */
public class TimeTable {
    private UniqueEnrollmentList enrollments = new UniqueEnrollmentList();

    public TimeTable() {
    }

    public TimeTable(List<Enrollment> enrollments) {
        enrollments.forEach(this.enrollments::add);
    }

    public TimeTable(UniqueEnrollmentList enrollments) {
        enrollments.forEach(this.enrollments::add);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    public boolean hasEnrollment(Enrollment enrollment) {
        return enrollments.contains(enrollment);
    }

    /**
     * Returns true if the timetable contains no enrollments.
     *
     * @return {@code true} the timetable contains no enrollments
     */
    public boolean isEmpty() {
        return enrollments.isEmpty();
    }

    public Enrollment getEnrollment(ModuleCode moduleCode) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getModuleCode().equals(moduleCode)) {
                return enrollment;
            }
        }
        throw new EnrollmentNotFoundException();
    }

    public UniqueEnrollmentList getEnrollments() {
        return enrollments;
        //return enrollments.asUnmodifiableObservableList(); // TODO: replace with ObservableList<Enrollment>
    }

    public void setEnrollments(UniqueEnrollmentList enrollments) {
        this.enrollments = enrollments;
    }

    public List<ModuleCode> getModuleCodes() {
        return enrollments.stream().map(Enrollment::getModuleCode).collect(Collectors.toList());
    }

    public boolean hasModuleCode(ModuleCode moduleCode) {
        return enrollments.hasModuleCode(moduleCode);
    }

    public void removeModuleCode(ModuleCode moduleCode) {
        enrollments.removeIf(enrollment -> enrollment.getModuleCode().equals(moduleCode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollments);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TimeTable)) {
            return false;
        } else {
            return enrollments.equals(((TimeTable) other).enrollments);
        }
    }
}
//@@author
