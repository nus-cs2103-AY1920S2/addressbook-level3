package seedu.zerotoone.logic.parser.workout;

import static java.util.Objects.requireNonNull;

import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class WorkoutParserUtil extends ParserUtil {
    /**
     * Parses a {@code String workoutName} into a {@code WorkoutName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code workoutName} is invalid.
     */
    public static WorkoutName parseWorkoutName(String workoutName) throws ParseException {
        requireNonNull(workoutName);
        String trimmedWorkoutName = workoutName.trim();
        if (!WorkoutName.isValidWorkoutName(trimmedWorkoutName)) {
            throw new ParseException(WorkoutName.MESSAGE_CONSTRAINTS);
        }
        return new WorkoutName(trimmedWorkoutName);
    }

    // /**
    //  * Parses a {@code String numReps} into a {@code NumReps}.
    //  * Leading and trailing whitespaces will be trimmed.
    //  *
    //  * @throws ParseException if the given {@code numReps} is invalid.
    //  */
    // public static List<Exercise> parseWorkoutExerciseList(String numReps) throws ParseException {
    //     requireNonNull(numReps);
    //     String trimmedNumReps = numReps.trim();
    //     if (!NumReps.isValidNumReps(trimmedNumReps)) {
    //         throw new ParseException(NumReps.MESSAGE_CONSTRAINTS);
    //     }
    //     return new NumReps(trimmedNumReps);
    // }
}
