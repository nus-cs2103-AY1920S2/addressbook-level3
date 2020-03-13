package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Time;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String time} into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String step} into an {@code Step}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code step} is invalid.
     */
    public static Step parseStep(String step) throws ParseException {
        requireNonNull(step);
        String trimmedStep = step.trim();
        if (!Step.isValidStep(trimmedStep)) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        return new Step(trimmedStep);
    }

    /**
     * Parses a {@code String goal} into a {@code Goal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code goal} is invalid.
     */
    public static Goal parseGoal(String goal) throws ParseException {
        requireNonNull(goal);
        String trimmedGoal = goal.trim();
        if (!Goal.isValidGoalName(trimmedGoal)) {
            throw new ParseException(Goal.MESSAGE_CONSTRAINTS);
        }
        return new Goal(trimmedGoal);
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code Set<Goal>}.
     */
    public static Set<Goal> parseGoals(Collection<String> goals) throws ParseException {
        requireNonNull(goals);
        final Set<Goal> goalSet = new HashSet<>();
        for (String goalName : goals) {
            goalSet.add(parseGoal(goalName));
        }
        return goalSet;
    }
}
