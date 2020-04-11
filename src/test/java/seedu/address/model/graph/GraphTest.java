package seedu.address.model.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_REPS;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_WEIGHT;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_ENDDATE;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_EXERCISE_NAME;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_STARTDATE;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.ExerciseName;

public class GraphTest {
    @Test
    public void equals() {
        Graph graph1 = new Graph(new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_REPS),
                new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));
        Graph graph2 = new Graph(new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_WEIGHT),
                new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));
        Graph graph1Copy = new Graph(new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_REPS),
                new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));

        // same values -> returns true
        assertTrue(graph1.equals(graph1Copy));

        // same object -> returns true
        assertTrue(graph1.equals(graph1));

        // null -> returns false
        assertFalse(graph1.equals(null));

        // different type -> returns false
        assertFalse(graph1.equals(5));

        // different Graph -> returns false
        assertFalse(graph1.equals(graph2));
    }
}
