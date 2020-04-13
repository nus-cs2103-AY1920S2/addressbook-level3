package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

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

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywordsExpenses_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("Chicken"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Chicken Rice").buildExpense()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Duck", "Chicken"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Duck Chicken").buildExpense()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Chicken", "Duck"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Beef Duck").buildExpense()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("dUCk", "chICkEn"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Duck Chicken").buildExpense()));
    }

    @Test
    public void test_nameContainsKeywordsIncome_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("Allowance"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance from my mother").buildIncome()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Allowance", "TA Salary"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance Salary").buildIncome()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Allowance", "Salary"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Ang Bao Salary").buildIncome()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("AlLOwanCe", "Ta SalARy"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance TA Salary").buildIncome()));
    }

    @Test
    public void test_nameContainsSubKeywordsExpenses_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("Chic"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Chicken Rice").buildExpense()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Du", "Chic"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Duck Chicken").buildExpense()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Chi", "Du"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Beef Duck").buildExpense()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("dU", "CkEn"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Duck Chicken").buildExpense()));
    }

    @Test
    public void test_nameContainsSubKeywordsIncome_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("All"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance from my mother").buildIncome()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("ance", "lary"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance Salary").buildIncome()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Allow", "Sal"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Ang Bao Salary").buildIncome()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("AlLO", "ARy"));
        assertTrue(predicate.test(new TransactionBuilder().withDescription("Allowance TA Salary").buildIncome()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsExpense_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Rice").buildExpense()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Chicken"));
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Beef Duck").buildExpense()));

        // Keywords match amount, date and tag, but does not match name
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("12345", "12/12/1999", "Food"));
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Duck Rice").withAmount("12345")
                .withDate("12/12/1999").withTag("Food").buildExpense()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsIncome_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate =
                new DescriptionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Rice").buildIncome()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Chicken"));
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Beef Duck").buildIncome()));

        // Keywords match amount, date and tag, but does not match name
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("12345", "12/12/1999", "Family"));
        assertFalse(predicate.test(new TransactionBuilder().withDescription("Allowance").withAmount("12345")
                .withDate("12/12/1999").withTag("Family").buildIncome()));
    }
}
