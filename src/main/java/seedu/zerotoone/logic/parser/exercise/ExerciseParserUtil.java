package seedu.zerotoone.logic.parser.exercise;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.exercise.ExerciseName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ExerciseParserUtil extends ParserUtil {
    private static final Logger logger = LogsCenter.getLogger(ExerciseParserUtil.class);

    /**
     * Parses a {@code String exerciseName} into a {@code ExerciseName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code exerciseName} is invalid.
     */
    public static ExerciseName parseExerciseName(String exerciseName) throws ParseException {
        logger.info("Parsing: " + exerciseName);

        requireNonNull(exerciseName);
        String trimmedExerciseName = exerciseName.trim();
        if (!ExerciseName.isValidExerciseName(trimmedExerciseName)) {
            throw new ParseException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        return new ExerciseName(trimmedExerciseName);
    }
}
