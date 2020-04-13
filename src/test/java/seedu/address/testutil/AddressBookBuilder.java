package seedu.address.testutil;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code StudentTAble ab = new AddressBookBuilder().withStudent("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private StudentTAble studentTAble;

    public AddressBookBuilder() {
        studentTAble = new StudentTAble();
    }

    public AddressBookBuilder(StudentTAble studentTAble) {
        this.studentTAble = studentTAble;
    }

    /**
     * Adds a new {@code Student} to the {@code StudentTAble} that we are building.
     */
    public AddressBookBuilder withStudent(Student student) {
        studentTAble.addStudent(student);
        return this;
    }

    public StudentTAble build() {
        return studentTAble;
    }
}
