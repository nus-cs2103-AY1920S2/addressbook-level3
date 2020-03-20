package tatracker.testutil;

import tatracker.model.TaTracker;
import tatracker.model.student.Student;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaTracker ab = new AddressBookBuilder().withStudent("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TaTracker taTracker;

    public AddressBookBuilder() {
        taTracker = new TaTracker();
    }

    public AddressBookBuilder(TaTracker taTracker) {
        this.taTracker = taTracker;
    }

    /**
     * Adds a new {@code Student} to the {@code TaTracker} that we are building.
     */
    public AddressBookBuilder withStudent(Student student) {
        taTracker.addStudent(student);
        return this;
    }

    public TaTracker build() {
        return taTracker;
    }
}
