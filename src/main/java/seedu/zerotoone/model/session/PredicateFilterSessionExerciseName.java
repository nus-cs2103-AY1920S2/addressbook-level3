package seedu.zerotoone.model.session;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.zerotoone.commons.util.StringUtil;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Tests that a {@code Session}'s {@code Name} matches any of the keywords given.
 */
public class PredicateFilterSessionExerciseName implements Predicate<Session> {
    private final List<String> keywords;

    public PredicateFilterSessionExerciseName(String keyword) {
        this.keywords = Arrays.asList(keyword.split("\\s+"));
    }

    @Override
    public boolean test(Session session) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(session.getExerciseName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PredicateFilterSessionExerciseName // instanceof handles nulls
                && keywords.equals(((PredicateFilterSessionExerciseName) other).keywords)); // state check
    }

}
