package seedu.zerotoone.model.workout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.testutil.workout.WorkoutBuilder;

public class PredicateFilterWorkoutNameTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "firstPredicate";
        String secondPredicateKeyword = "secondPredicate";

        PredicateFilterWorkoutName firstPredicate = new PredicateFilterWorkoutName(firstPredicateKeyword);
        PredicateFilterWorkoutName secondPredicate = new PredicateFilterWorkoutName(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PredicateFilterWorkoutName firstPredicateCopy = new PredicateFilterWorkoutName(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different workout -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PredicateFilterWorkoutName predicate = new PredicateFilterWorkoutName("Arms");
        assertTrue(predicate.test(new WorkoutBuilder().withWorkoutName("Arms Workout").build()));

        // Full Keyword
        predicate = new PredicateFilterWorkoutName("Arms Workout");
        assertTrue(predicate.test(new WorkoutBuilder().withWorkoutName("Arms Workout").build()));

        // Mixed-case keywords
        predicate = new PredicateFilterWorkoutName("aRmS");
        assertTrue(predicate.test(new WorkoutBuilder().withWorkoutName("Arms Workout").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PredicateFilterWorkoutName predicate = new PredicateFilterWorkoutName("Arms");
        assertFalse(predicate.test(new WorkoutBuilder().withWorkoutName("Legs Workout").build()));
    }
}
