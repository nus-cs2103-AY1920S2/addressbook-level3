package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

public class DateContainsKeywordsPredicateTest {

    private List<String> firstPredicateKeywordList = Collections.singletonList("11/11/2011");
    private List<String> secondPredicateKeywordList = Arrays.asList("11/11/2011", "12/12/2012");

    private DateContainsKeywordsPredicate firstPredicate =
            new DateContainsKeywordsPredicate(firstPredicateKeywordList);
    private DateContainsKeywordsPredicate secondPredicate =
            new DateContainsKeywordsPredicate(secondPredicateKeywordList);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateContainsKeywordsPredicate firstPredicateCopy =
                new DateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateContainsKeywords_returnsTrue() {
        // One keyword
        assertTrue(firstPredicate.test(new TransactionBuilder().withDate("11/11/2011").buildExpense()));

        // Only one matching keyword
        assertTrue(secondPredicate.test(new TransactionBuilder().withDate("11/11/2011").buildExpense()));
    }

    @Test
    public void test_dateDoesNotContainKeywordsExpense_returnsFalse() {
        // Zero keywords
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().buildExpense()));

        // Non-matching keyword
        assertFalse(firstPredicate.test(new TransactionBuilder().buildExpense()));
    }

}
