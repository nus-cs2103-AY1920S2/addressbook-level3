package seedu.zerotoone.logic.parser.workout;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class WorkoutParserUtil extends ParserUtil {
    private static final Logger logger = LogsCenter.getLogger(WorkoutParserUtil.class);

    /**
     * Parses a {@code String workoutName} into a {@code WorkoutName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code workoutName} is invalid.
     */
    public static WorkoutName parseWorkoutName(String workoutName) throws ParseException {
        logger.info("Parsing workout name: " + workoutName);

        requireNonNull(workoutName);
        String trimmedWorkoutName = workoutName.trim();
        if (!WorkoutName.isValidWorkoutName(trimmedWorkoutName)) {
            throw new ParseException(WorkoutName.MESSAGE_CONSTRAINTS);
        }
        return new WorkoutName(trimmedWorkoutName);
    }
}
