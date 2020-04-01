package seedu.address.testutil;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Student objects.
 * Example usage: <br>
 * {@code Student ab = new StudentBuilder().withModule("CS2103T").build();}
 */
public class StudentBuilder {

    private Student student;

    public StudentBuilder() {
        student = new Student();
    }

    public StudentBuilder(Student student) {
        this.student = student;
    }

    public StudentBuilder withName(Name name) {
        this.student.setName(name);
        return this;
    }

    public Student build() {
        return student;
    }
}
