package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_AS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_CONQUER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_HOME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_ONLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_PROG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BROKEN_FIND_WORDS_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_PROGRAMMING;
import static seedu.address.testutil.TypicalTasks.nonScheduledProgrammingAssignmentTask;
import static seedu.address.testutil.TypicalTasks.nonScheduledProgrammingTask;
import static seedu.address.testutil.TypicalTasks.scheduledAssignmentTask;
import static seedu.address.testutil.TypicalTasks.scheduledHomeworkTask;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class TaskContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(VALID_FIND_WORDS_PROGRAMMING);
        List<String> secondPredicateKeywordList = Arrays.asList(
                VALID_BROKEN_FIND_WORDS_AS, VALID_BROKEN_FIND_WORDS_HOME);

        TaskContainsKeywordsPredicate firstPredicate = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskContainsKeywordsPredicate secondPredicate = new TaskContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordsPredicate firstPredicateCopy = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskDescriptionContainsKeywords_returnsTrue() {
        // full match one keyword
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(
                Collections.singletonList(VALID_FIND_WORDS_PROGRAMMING));
        assertTrue(predicate.test(nonScheduledProgrammingTask)); // matches

        // partial match one keyword
        predicate = new TaskContainsKeywordsPredicate(
                Collections.singletonList(VALID_BROKEN_FIND_WORDS_PROG));
        assertTrue(predicate.test(nonScheduledProgrammingTask)); // contains "prog"

        // full match, match all multiple keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_FIND_WORDS_ASSIGNMENT, VALID_FIND_WORDS_PROGRAMMING));
        assertTrue(predicate.test(nonScheduledProgrammingAssignmentTask)); // matches both keywords

        // partial match, match all multiple keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_AS, VALID_BROKEN_FIND_WORDS_PROG));
        assertTrue(predicate.test(nonScheduledProgrammingAssignmentTask)); // matches both keywords

        // full match, only one matching keyword
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_FIND_WORDS_ASSIGNMENT, VALID_FIND_WORDS_PROGRAMMING));
        assertTrue(predicate.test(scheduledAssignmentTask)); // matches assignment

        // partial match, only one matching keyword
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_AS, VALID_BROKEN_FIND_WORDS_PROG));
        assertTrue(predicate.test(scheduledAssignmentTask)); // matches assignment

        // Mixed-case keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_PROG,
                        VALID_BROKEN_FIND_WORDS_AS, VALID_BROKEN_FIND_WORDS_HOME));
        assertTrue(predicate.test(scheduledHomeworkTask)); // matches homework

        // full match, duplicate keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_FIND_WORDS_ASSIGNMENT, VALID_FIND_WORDS_ASSIGNMENT));
        assertTrue(predicate.test(scheduledAssignmentTask)); // matches assignment

        // partial match, duplicate keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_AS, VALID_BROKEN_FIND_WORDS_AS));
        assertTrue(predicate.test(scheduledAssignmentTask)); // matches assignment

        // partial match, duplicate keywords mixed with unique keywords
        predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_PROG,
                        VALID_BROKEN_FIND_WORDS_PROG,
                        VALID_BROKEN_FIND_WORDS_SLEEP));
        assertTrue(predicate.test(nonScheduledProgrammingTask)); // matches programming
    }

    @Test
    public void test_taskDescriptionDoesNotContainKeywords_returnsFalse() {
        // all non-matching keywords when keywords are the most flexible (broken & random cases)
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(
                Arrays.asList(VALID_BROKEN_FIND_WORDS_SLEEP,
                        VALID_BROKEN_FIND_WORDS_ONLINE,
                        VALID_BROKEN_FIND_WORDS_CONQUER));
        assertFalse(predicate.test(nonScheduledProgrammingAssignmentTask));
    }
}


