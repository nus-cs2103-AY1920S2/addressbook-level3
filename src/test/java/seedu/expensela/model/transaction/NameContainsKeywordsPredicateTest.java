package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Apples"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Apples Bananas").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Apples", "Bananas"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Apples Bananas").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bananas", "Carrots"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Apples Carrots").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aPplEs", "baNNaS"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Apples Bananas").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withName("Apples").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carrots"));
        assertFalse(predicate.test(new TransactionBuilder().withName("Apples Bananas").build()));

        // Keywords match amount, date, remark and category, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("5.00", "2020-02-07", "Make", "FOOD"));
        assertFalse(predicate.test(new TransactionBuilder().withName("Apples")
                .withAmount("5.00", false)
                .withDate("2020-02-07").withRemark("Make pie").withCategory("FOOD").build()));
    }
}
