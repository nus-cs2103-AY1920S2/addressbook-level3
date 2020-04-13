package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.ReminderBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first?");
        List<String> secondPredicateKeywordList = Arrays.asList("first?", "second?");

        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy =
                new DescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different reminder -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() throws ParseException {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
                Collections.singletonList("Midterms"));
        assertTrue(predicate.test(new ReminderBuilder().withDescription("Midterms Papers").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Midterms", "Papers"));
        assertTrue(predicate.test(new ReminderBuilder().withDescription("Midterms Papers").build()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Papers", "T03"));
        assertTrue(predicate.test(new ReminderBuilder().withDescription("Midterms T03").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("mIDterms", "pApeRS"));
        assertTrue(predicate.test(new ReminderBuilder().withDescription("Midterms Papers").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() throws ParseException {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ReminderBuilder().withDescription("Midterms").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("T03"));
        assertFalse(predicate.test(new ReminderBuilder().withDescription("Midterms Papers").build()));

        // Keywords match date and time, but does not match description
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("2022-04-05", "17:00"));
        assertFalse(predicate.test(new ReminderBuilder().withDescription("Midterms").withDate("2022-04-05")
                .withTime("17:00").build()));
    }
}
