package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Planner;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName(new Name("Alice")).build();
    public static final Student BOB = new StudentBuilder().withName(new Name("Bob")).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {
    } // prevents instantiation

    /**
     * Returns an {@code Planner} with all the typical students.
     */
    public static Planner getTypicalPlanner() {
        Planner planner = new Planner();
        for (Student student : getTypicalStudents()) {
            planner.addStudent(student);
        }
        return planner;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB));
    }
}
