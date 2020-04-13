package fithelper.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fithelper.testutil.EntryBuilder;

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

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Chicken"));
        assertTrue(predicate.test(new EntryBuilder().withName("Chicken rice").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Chicken", "rice"));
        assertTrue(predicate.test(new EntryBuilder().withName("Chicken Rice").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("chicken", "pork"));
        assertTrue(predicate.test(new EntryBuilder().withName("chicken rice").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cHicken", "riCe"));
        assertTrue(predicate.test(new EntryBuilder().withName("Chicken Rice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EntryBuilder().withName("Chicken").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Pork"));
        assertFalse(predicate.test(new EntryBuilder().withName("Chicken rice").build()));


        // Keywords match location, calorie, time and type, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("utown", "200", "2020-05-12-12:00", "food"));
        assertFalse(predicate.test(new EntryBuilder().withName("Chicken").withLocation("utown")
                .withCalorie("200").withTime("2020-05-12-12:00").withType("food").build()));
    }
}
