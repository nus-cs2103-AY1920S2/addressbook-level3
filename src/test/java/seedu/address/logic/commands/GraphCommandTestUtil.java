package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AXIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

/**
 * Contains helper static variables for testing graph commands.
 */
public class GraphCommandTestUtil {

    // valid
    public static final String VALID_EXERCISE_NAME = "pushup";
    public static final String VALID_AXIS_REPS = "reps";
    public static final String VALID_AXIS_WEIGHT = "weight";
    public static final String VALID_STARTDATE = "01-01-2020";
    public static final String VALID_ENDDATE = "01-04-2020";

    public static final String VALID_EXERCISE_NAME_DESC =
            " " + PREFIX_EXERCISE_NAME + VALID_EXERCISE_NAME;
    public static final String VALID_AXIS_REPS_DESC =
            " " + PREFIX_AXIS + VALID_AXIS_REPS;
    public static final String VALID_AXIS_WEIGHT_DESC =
            " " + PREFIX_AXIS + VALID_AXIS_WEIGHT;
    public static final String VALID_STARTDATE_DESC =
            " " + PREFIX_STARTDATE + VALID_STARTDATE;
    public static final String VALID_ENDDATE_DESC =
            " " + PREFIX_ENDDATE + VALID_ENDDATE;

    public static final String VALID_GRAPH_USER_INPUT =
            VALID_EXERCISE_NAME_DESC + VALID_AXIS_REPS_DESC
            + VALID_STARTDATE_DESC + VALID_ENDDATE_DESC;

    // invalid
    public static final String INVALID_EXERCISE_NAME = "pushup&";
    public static final String INVALID_AXIS = "repss";
    public static final String INVALID_STARTDATE = "1-1-2";
    public static final String INVALID_ENDDATE = "1-1-2";

    public static final String INVALID_EXERCISE_NAME_DESC =
            " " + PREFIX_EXERCISE_NAME + INVALID_EXERCISE_NAME;
    public static final String INVALID_AXIS_DESC =
            " " + PREFIX_AXIS + INVALID_AXIS;
    public static final String INVALID_STARTDATE_DESC =
            " " + PREFIX_STARTDATE + INVALID_STARTDATE;
    public static final String INVALID_ENDDATE_DESC =
            " " + PREFIX_ENDDATE + INVALID_ENDDATE;

}
