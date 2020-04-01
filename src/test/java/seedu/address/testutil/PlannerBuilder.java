package seedu.address.testutil;

import seedu.address.model.Planner;
import seedu.address.model.module.Module;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Planner objects.
 * Example usage: <br>
 * {@code Planner ab = new PlannerBuilder().withModule("CS2103T").build();}
 */
public class PlannerBuilder {

    private Planner planner;

    public PlannerBuilder() {
        planner = new Planner();
    }

    public PlannerBuilder(Planner planner) {
        this.planner = planner;
    }

    public PlannerBuilder withStudent(Student student) {
        this.planner.addStudent(student);
        return this;
    }

    public PlannerBuilder withModule(Module module) {
        this.planner.addModule(module);
        return this;
    }


    public Planner build() {
        return planner;
    }
}
