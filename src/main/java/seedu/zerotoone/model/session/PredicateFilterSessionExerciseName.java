package seedu.zerotoone.model.session;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.zerotoone.commons.util.StringUtil;

/**
 * Tests that a {@code Session}'s {@code Name} matches any of the keywords given.
 */
public class PredicateFilterSessionExerciseName implements Predicate<CompletedExercise> {
    private final List<String> keywords;

    public PredicateFilterSessionExerciseName(String keyword) {
        this.keywords = Arrays.asList(keyword.split("\\s+"));
    }

    @Override
    public boolean test(CompletedExercise completedExercise) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(completedExercise.getExerciseName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PredicateFilterSessionExerciseName // instanceof handles nulls
                && keywords.equals(((PredicateFilterSessionExerciseName) other).keywords)); // state check
    }

}
