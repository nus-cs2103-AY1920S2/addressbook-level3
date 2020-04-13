package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

public class TagContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }


    @Test
    public void test_nameContainsKeywordsExpenses_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Food"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Food").buildExpense()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Duck", "Chicken"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Duck").buildExpense()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("dUCk", "chICkEn"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Duck").buildExpense()));
    }


    @Test
    public void test_nameContainsKeywordsIncome_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Allowance"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Allowance", "TA Salary"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Allowance", "Salary"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Salary").buildIncome()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("AlLOwanCe", "Ta SalARy"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));
    }


    @Test
    public void test_nameContainsSubKeywordsExpenses_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Chic"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Chicken").buildExpense()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Du", "Chic"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Duck").buildExpense()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Chi", "Du"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Duck").buildExpense()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("dU", "CkEn"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Duck").buildExpense()));
    }


    @Test
    public void test_nameContainsSubKeywordsIncome_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("All"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("ance", "all"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Allow", "Sal"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Salary").buildIncome()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("AlLO", "ARy"));
        assertTrue(predicate.test(new TransactionBuilder().withTag("Allowance").buildIncome()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsExpense_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withTag("Rice").buildExpense()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Chicken"));
        assertFalse(predicate.test(new TransactionBuilder().withTag("Beef").buildExpense()));

        // Keywords match amount, date and tag, but does not match name
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "12/12/1999", "Food"));
        assertFalse(predicate.test(new TransactionBuilder().withTag("Duck").withDescription("Food").withAmount("12345")
                .withDate("12/12/1999").buildExpense()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsIncome_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withTag("Rice").buildIncome()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Chicken"));
        assertFalse(predicate.test(new TransactionBuilder().withTag("Beef").buildIncome()));

        // Keywords match amount, date and tag, but does not match name
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "12/12/1999", "Family"));
        assertFalse(predicate.test(new TransactionBuilder().withTag("Allowance").withAmount("12345")
                .withDate("12/12/1999").withDescription("Family").buildIncome()));
    }

}
