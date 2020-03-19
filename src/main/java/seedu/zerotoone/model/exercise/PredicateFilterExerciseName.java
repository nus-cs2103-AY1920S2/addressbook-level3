package seedu.zerotoone.model.exercise;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.zerotoone.commons.util.StringUtil;

/**
 * Tests that a {@code Exercise}'s {@code Name} matches any of the keywords given.
 */
public class PredicateFilterExerciseName implements Predicate<Exercise> {
    private final List<String> keywords;

    public PredicateFilterExerciseName(String keyword) {
        this.keywords = Arrays.asList(keyword.split("\\s+"));
    }

    @Override
    public boolean test(Exercise exercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(exercise.getExerciseName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PredicateFilterExerciseName // instanceof handles nulls
                && keywords.equals(((PredicateFilterExerciseName) other).keywords)); // state check
    }

}
