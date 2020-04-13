package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

class CategoryEqualsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("FOOD");
        List<String> secondPredicateKeywordList = Collections.singletonList("GROCERIES");

        CategoryEqualsKeywordPredicate firstPredicate = new CategoryEqualsKeywordPredicate(firstPredicateKeywordList);
        CategoryEqualsKeywordPredicate secondPredicate = new CategoryEqualsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CategoryEqualsKeywordPredicate firstPredicateCopy =
                new CategoryEqualsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different category -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoryEqualsKeyword_returnsTrue() {
        // One matching keyword
        CategoryEqualsKeywordPredicate predicate = new CategoryEqualsKeywordPredicate(
                Collections.singletonList("HEALTH"));
        assertTrue(predicate.test(new TransactionBuilder().withCategory("HEALTH").build()));
    }

    @Test
    public void test_categoryDoesNotEqualKeyword_returnsFalse() {
        // Zero keywords
        CategoryEqualsKeywordPredicate predicate = new CategoryEqualsKeywordPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new TransactionBuilder().withCategory("FOOD").build()));

        // Non-matching keyword
        predicate = new CategoryEqualsKeywordPredicate(Arrays.asList("FOOD"));
        assertFalse(predicate.test(new TransactionBuilder().withCategory("UTILITIES").build()));

        // Keywords match month, but does not match category
        predicate = new CategoryEqualsKeywordPredicate(Arrays.asList("2000-01"));
        assertFalse(predicate.test(new TransactionBuilder().withCategory("GROCERIES").build()));

        // Keywords is not a valid category
        predicate = new CategoryEqualsKeywordPredicate(Arrays.asList("GIFTS"));
        assertFalse(predicate.test(new TransactionBuilder().withCategory("GROCERIES").build()));

        // Mixed-case keywords
        predicate = new CategoryEqualsKeywordPredicate(Arrays.asList("fOoD"));
        assertFalse(predicate.test(new TransactionBuilder().withCategory("FOOD").build()));
    }
}
