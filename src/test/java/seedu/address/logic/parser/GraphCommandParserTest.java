package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GraphCommandTestUtil.INVALID_AXIS_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.INVALID_ENDDATE_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_REPS;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_AXIS_REPS_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_ENDDATE;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_ENDDATE_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_EXERCISE_NAME;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_GRAPH_USER_INPUT;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_STARTDATE;
import static seedu.address.logic.commands.GraphCommandTestUtil.VALID_STARTDATE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GraphCommand;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.graph.Axis;
import seedu.address.model.graph.EndDate;
import seedu.address.model.graph.Graph;
import seedu.address.model.graph.StartDate;

public class GraphCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE);

    private GraphCommandParser parser = new GraphCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(
                parser, VALID_AXIS_REPS_DESC + VALID_STARTDATE_DESC + VALID_ENDDATE_DESC, MESSAGE_INVALID_FORMAT);

        // no axis specified
        assertParseFailure(
                parser, VALID_EXERCISE_NAME_DESC + VALID_STARTDATE_DESC + VALID_ENDDATE_DESC, MESSAGE_INVALID_FORMAT);

        // no startdate specified
        assertParseFailure(
                parser, VALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC + VALID_ENDDATE_DESC, MESSAGE_INVALID_FORMAT);

        // no enddate specified
        assertParseFailure(
                parser, VALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC + VALID_STARTDATE_DESC, MESSAGE_INVALID_FORMAT);

        // no exercise name, no axis, no start date and no end date specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_GRAPH_USER_INPUT;
        Graph graph = new Graph(new ExerciseName(VALID_EXERCISE_NAME), new Axis(VALID_AXIS_REPS),
            new StartDate(VALID_STARTDATE), new EndDate(VALID_ENDDATE));
        GraphCommand expectedCommand = new GraphCommand(graph);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid exercise name
        assertParseFailure(parser, INVALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC
                + VALID_STARTDATE_DESC + VALID_ENDDATE_DESC, ExerciseName.MESSAGE_CONSTRAINTS);

        // invalid axis
        assertParseFailure(parser, VALID_EXERCISE_NAME_DESC + INVALID_AXIS_DESC
                + VALID_STARTDATE_DESC + VALID_ENDDATE_DESC, Axis.MESSAGE_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, VALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC
                + INVALID_STARTDATE_DESC + VALID_ENDDATE_DESC, StartDate.MESSAGE_CONSTRAINTS);

        // invalid end date
        assertParseFailure(parser, VALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC
                + VALID_STARTDATE_DESC + INVALID_ENDDATE_DESC, EndDate.MESSAGE_CONSTRAINTS);
    }
}
