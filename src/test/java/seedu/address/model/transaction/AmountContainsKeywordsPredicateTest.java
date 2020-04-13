package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

public class AmountContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("1", "2");

        AmountContainsKeywordsPredicate firstPredicate =
                new AmountContainsKeywordsPredicate(firstPredicateKeywordList);
        AmountContainsKeywordsPredicate secondPredicate =
                new AmountContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AmountContainsKeywordsPredicate firstPredicateCopy =
                new AmountContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }


    @Test
    public void test_amountContainsKeywordsExpenses_returnsTrue() {
        // One keyword
        AmountContainsKeywordsPredicate predicate =
                new AmountContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount("1.56").buildExpense()));

        // Only one matching keyword
        predicate = new AmountContainsKeywordsPredicate(Arrays.asList("1", "9"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount("1.55").buildExpense()));
    }

    @Test
    public void test_amountContainsKeywordsIncome_returnsTrue() {
        // One keyword
        AmountContainsKeywordsPredicate predicate =
                new AmountContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount("1.56").buildIncome()));

        // Only one matching keyword
        predicate = new AmountContainsKeywordsPredicate(Arrays.asList("1", "9.11"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount("1.55").buildIncome()));
    }

    @Test
    public void test_amountDoesNotContainKeywordsExpense_returnsFalse() {
        // Zero keywords
        AmountContainsKeywordsPredicate predicate =
                new AmountContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withAmount("1.33").buildExpense()));

        // Non-matching keyword
        predicate = new AmountContainsKeywordsPredicate(Arrays.asList("1"));
        assertFalse(predicate.test(new TransactionBuilder().withAmount("3.55").buildExpense()));
    }


    @Test
    public void test_amountDoesNotContainKeywordsIncome_returnsFalse() {
        // Zero keywords
        AmountContainsKeywordsPredicate predicate =
                new AmountContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withAmount("1.22").buildIncome()));

        // Non-matching keyword
        predicate = new AmountContainsKeywordsPredicate(Arrays.asList("6"));
        assertFalse(predicate.test(new TransactionBuilder().withAmount("1.33").buildIncome()));

    }
}
