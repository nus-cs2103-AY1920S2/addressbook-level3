package seedu.zerotoone.model.log;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.testutil.log.LogBuilder;

class PredicateFilterLogWorkoutNameTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "firstPredicate";
        String secondPredicateKeyword = "secondPredicate";

        PredicateFilterLogWorkoutName firstPredicate = new PredicateFilterLogWorkoutName(firstPredicateKeyword);
        PredicateFilterLogWorkoutName secondPredicate = new PredicateFilterLogWorkoutName(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PredicateFilterLogWorkoutName firstPredicateCopy = new PredicateFilterLogWorkoutName(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different exercise -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstring_returnsTrue() {
        // One word substring
        PredicateFilterLogWorkoutName predicate = new PredicateFilterLogWorkoutName("Arms");
        assertTrue(predicate.test(new LogBuilder().withWorkoutName("Arms Day").build()));

        // Full word Substring
        predicate = new PredicateFilterLogWorkoutName("Arms Day");
        assertTrue(predicate.test(new LogBuilder().withWorkoutName("Arms Day").build()));

        // Mixed-case substrings
        predicate = new PredicateFilterLogWorkoutName("aRmS");
        assertTrue(predicate.test(new LogBuilder().withWorkoutName("Arms Day").build()));

        // Partial substring
        predicate = new PredicateFilterLogWorkoutName("arms");
        assertTrue(predicate.test(new LogBuilder().withWorkoutName("Arms Day").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        PredicateFilterLogWorkoutName predicate = new PredicateFilterLogWorkoutName("Arms");
        assertFalse(predicate.test(new LogBuilder().withWorkoutName("Days").build()));
    }
}
