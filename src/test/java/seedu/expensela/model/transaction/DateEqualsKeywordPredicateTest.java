package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

class DateEqualsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("2020-01");
        List<String> secondPredicateKeywordList = Collections.singletonList("2020-02");

        DateEqualsKeywordPredicate firstPredicate = new DateEqualsKeywordPredicate(firstPredicateKeywordList);
        DateEqualsKeywordPredicate secondPredicate = new DateEqualsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateEqualsKeywordPredicate firstPredicateCopy = new DateEqualsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date month -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateEqualsKeyword_returnsTrue() {
        // One matching keyword
        DateEqualsKeywordPredicate predicate = new DateEqualsKeywordPredicate(
                Collections.singletonList("2020-01"));
        assertTrue(predicate.test(new TransactionBuilder().withDate("2020-01-01").build()));
    }

    @Test
    public void test_dateDoesNotEqualKeyword_returnsFalse() {
        // Zero keywords
        DateEqualsKeywordPredicate predicate = new DateEqualsKeywordPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new TransactionBuilder().withDate("2020-01-01").build()));

        // Non-matching keyword
        predicate = new DateEqualsKeywordPredicate(Arrays.asList("2020-02"));
        assertFalse(predicate.test(new TransactionBuilder().withDate("2020-01-01").build()));

        // Keywords match category, but does not match month
        predicate = new DateEqualsKeywordPredicate(Arrays.asList("FOOD"));
        assertFalse(predicate.test(new TransactionBuilder().withDate("2020-01-01").build()));

        // Keyword's month is not a valid month
        predicate = new DateEqualsKeywordPredicate(Arrays.asList("1999-13"));
        assertFalse(predicate.test(new TransactionBuilder().withDate("1999-12-31").build()));

        // Keyword's year is not a valid year
        predicate = new DateEqualsKeywordPredicate(Arrays.asList("1899-11"));
        assertFalse(predicate.test(new TransactionBuilder().withDate("1900-11-01").build()));
    }
}
