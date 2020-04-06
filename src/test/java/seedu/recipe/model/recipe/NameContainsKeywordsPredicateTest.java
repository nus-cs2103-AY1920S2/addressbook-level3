package seedu.recipe.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.recipe.testutil.RecipeBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeywords = "first";
        String secondPredicateKeywords = "first second";

        // Base predicate for comparison
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(false, firstPredicateKeywords);
        // Same isStrict, different keywords
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(false, secondPredicateKeywords);
        // Different isStrict, same keywords
        NameContainsKeywordsPredicate thirdPredicate =
                new NameContainsKeywordsPredicate(true, firstPredicateKeywords);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy =
                new NameContainsKeywordsPredicate(false, firstPredicateKeywords);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // same isStrict, different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different isStrict, same keywords -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameContainsKeywordsStrict_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(true, "Alice");
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(true, "Alice Bob");
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(true, "Bob Carol");
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(true, "aLIce bOB");
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsStrict_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(true, " ");
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(true, "Carol");
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Keywords match time and step, but does not match name
        predicate = new NameContainsKeywordsPredicate(true, "12345 alice@email.com Main Street");
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice").withTime("12345")
                .withSteps("Sample step").build()));
    }
}
