package tatracker.testutil;

import tatracker.model.TaTracker;
import tatracker.model.module.Module;
import tatracker.model.student.Student;

/**
 * A utility class to help with building TA-Tracker objects.
 * Example usage: <br>
 *     {@code TaTracker ab = new TaTrackerBuilder().withStudent("John", "Doe").build();}
 */
public class TaTrackerBuilder {

    private TaTracker taTracker;

    public TaTrackerBuilder() {
        taTracker = new TaTracker();
    }

    public TaTrackerBuilder(TaTracker taTracker) {
        this.taTracker = taTracker;
    }

    /**
     * Adds a new {@code Student} to the {@code TaTracker} that we are building.
     */
    public TaTrackerBuilder withStudent(Student student) {
        taTracker.addStudent(student);
        return this;
    }

    /**
     * Adds a new module to the ta tracker.
     */
    public TaTrackerBuilder withModule(Module module) {
        taTracker.addModule(module);
        return this;
    }

    public TaTracker build() {
        return taTracker;
    }
}
