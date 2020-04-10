package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_REPS;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_WEIGHT;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_ENDDATE;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_EXERCISE_NAME;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_STARTDATE;
import static seedu.address.testutil.TypicalClients.getTypicalFitBiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ClientInView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.graph.Axis;
import seedu.address.model.graph.EndDate;
import seedu.address.model.graph.Graph;
import seedu.address.model.graph.StartDate;

public class GraphCommandTest {
    private Graph validGraphFirst;
    private Graph validGraphSecond;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFitBiz(), new UserPrefs(), new ClientInView());
        validGraphFirst = new Graph(
            new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_REPS),
            new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));
        validGraphSecond = new Graph(
            new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_WEIGHT),
            new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));
    }

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GraphCommand(null));
    }

    @Test
    public void execute_noClientInView_throwsCommandException() {
        GraphCommand graphCommand = new GraphCommand(validGraphFirst);

        assertCommandFailure(graphCommand, model, GraphCommand.MESSAGE_CLIENT_NOT_IN_VIEW);
    }

    @Test

    public void equals() {
        GraphCommand graphFirstCommand = new GraphCommand(validGraphFirst);
        GraphCommand graphSecondCommand = new GraphCommand(validGraphSecond);

        // same object -> returns true
        assertTrue(graphFirstCommand.equals(graphFirstCommand));

        // same values -> returns true
        GraphCommand graphFirstCommandCopy = new GraphCommand(validGraphFirst);
        assertTrue(graphFirstCommand.equals(graphFirstCommandCopy));

        // different types -> returns false
        assertFalse(graphFirstCommand.equals(1));

        // null -> returns false
        assertFalse(graphFirstCommand.equals(null));

        // different graph -> returns false
        assertFalse(graphFirstCommand.equals(graphSecondCommand));
    }
}
