package seedu.zerotoone.model.log;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.zerotoone.commons.util.StringUtil;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * Tests that a {@code Session}'s {@code Name} matches any of the keywords given.
 */
public class PredicateFilterLogExerciseName implements Predicate<CompletedWorkout> {
    private final List<String> keywords;

    public PredicateFilterLogExerciseName(String keyword) {
        this.keywords = Arrays.asList(keyword.split("\\s+"));
    }

    @Override
    public boolean test(CompletedWorkout completedWorkout) {
        return keywords.stream().anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(completedWorkout.getWorkoutName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PredicateFilterLogExerciseName // instanceof handles nulls
                && keywords.equals(((PredicateFilterLogExerciseName) other).keywords)); // state check
    }

}
