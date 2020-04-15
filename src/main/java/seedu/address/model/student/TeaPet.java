package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the tea-pet level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class TeaPet implements ReadOnlyTeaPet {

    private final UniqueStudentList students;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    public TeaPet() {}

    /**
     * Creates a TeaPet using the Students in the {@code toBeCopied}
     */
    public TeaPet(ReadOnlyTeaPet toBeCopied) {
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
     * Resets the existing data of this {@code TeaPet} with {@code newData}.
     */
    public void resetData(ReadOnlyTeaPet newData) {
        requireNonNull(newData);
        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in tea pet.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in tea pet.
     */
    public boolean hasStudentName(String student) {
        requireNonNull(student);
        boolean contains = false;
        for (Student stu : students) {
            if (stu.getName().fullName.equals(student)) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in tea pet.
     */
    public boolean hasStudentNameNonCaseSensitive(String student) {
        requireNonNull(student);
        boolean contains = false;
        for (Student stu : students) {
            if (stu.getName().fullName.toLowerCase().equals(student.toLowerCase())) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Adds a student to tea pet.
     * The student must not already exist in tea pet.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in tea pet.
     * The student identity of {@code editedStudent} must not be the same as another existing student in
     * tea pet.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);
        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code TeaPet}.
     * {@code key} must exist in tea pet.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TeaPet // instanceof handles nulls
                && students.equals(((TeaPet) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
