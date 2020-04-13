package seedu.zerotoone.logic.parser.exercise.set;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.util.ParserUtil;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class SetParserUtil extends ParserUtil {
    private static final Logger logger = LogsCenter.getLogger(SetParserUtil.class);

    /**
     * Parses a {@code String numReps} into a {@code NumReps}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numReps} is invalid.
     */
    public static NumReps parseNumReps(String numReps) throws ParseException {
        logger.fine("Parsing: " + numReps);

        requireNonNull(numReps);
        String trimmedNumReps = numReps.trim();
        if (!NumReps.isValidNumReps(trimmedNumReps)) {
            throw new ParseException(NumReps.MESSAGE_CONSTRAINTS);
        }
        return new NumReps(trimmedNumReps);
    }

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        logger.fine("Parsing: " + weight);

        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!Weight.isValidWeight(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }
        return new Weight(trimmedWeight);
    }
}
