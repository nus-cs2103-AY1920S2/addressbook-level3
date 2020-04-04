package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Scheduler;
import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@Assignment} objects to be used in tests
 */
public class TypicalAssignments {
    public static final Assignment CS3243_TUT = new AssignmentBuilder().withTitle("CS3243 Tut")
            .withDeadline("2020-04-25 13:00").withHours("4").build();
    public static final Assignment CS2106_ASSIGNMENT = new AssignmentBuilder().withTitle("CS2106 Assignment")
            .withDeadline("2020-04-26 14:00").withHours("10").build();
    public static final Assignment CS3244_TUT = new AssignmentBuilder().withTitle("CS3244 Tut")
            .withDeadline("2020-04-27 12:00").withHours("5").build();
    public static final Assignment CS2103_TP = new AssignmentBuilder().withTitle("CS2103 tP")
            .withDeadline("2020-04-27 14:00").withHours("11.5").build();
    public static final Assignment CS3243_PROJ = new AssignmentBuilder().withTitle("CS3243 Project")
            .withDeadline("2020-05-03 23:59").withHours("40").build();
    public static final Assignment CS3244_PROJ = new AssignmentBuilder().withTitle("CS3244 Project")
            .withDeadline("2020-05-11 23:59").withHours("40").withStatus("Completed").build();

    // Manually Added
    public static final Assignment IS1103_QUIZ = new AssignmentBuilder().withTitle("IS1103 Quiz")
            .withDeadline("2020-05-11 23:59").withHours("40").withStatus("Uncompleted").build();
    public static final Assignment CS2103_QUIZ = new AssignmentBuilder().withTitle("CS2103 Quiz")
            .withDeadline("2020-05-18 23:59").withHours("1").withStatus("Uncompleted").build();

    private TypicalAssignments() {}

    /**
     * Returns a scheduler with all the typical assignments in loaded.
     */
    public static Scheduler getTypicalScheduler() {
        Scheduler scheduler = new Scheduler();
        for (Assignment assignment : getTypicalAssignments()) {
            scheduler.addAssignment(assignment);
        }
        return scheduler;
    }

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(CS3243_TUT, CS2106_ASSIGNMENT, CS3244_TUT,
                CS2103_TP, CS3243_PROJ, CS3244_PROJ));
    }
}
