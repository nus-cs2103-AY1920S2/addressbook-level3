package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PeoplePhonePredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("9123");
        List<String> secondPredicateKeywordList = Arrays.asList("91234", "8826");

        PeoplePhonePredicate firstPredicate = new PeoplePhonePredicate(firstPredicateKeywordList);
        PeoplePhonePredicate secondPredicate = new PeoplePhonePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PeoplePhonePredicate firstPredicateCopy = new PeoplePhonePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PeoplePhonePredicate predicate = new PeoplePhonePredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Only one matching keyword
        predicate = new PeoplePhonePredicate(Arrays.asList("91234567", "8765"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneContainsPartialKeywords_returnsTrue() {
        // One partial keyword
        PeoplePhonePredicate predicate = new PeoplePhonePredicate(Collections.singletonList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple partial keyword
        predicate = new PeoplePhonePredicate(Arrays.asList("3456", "9123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Only one matching partial keyword
        predicate = new PeoplePhonePredicate(Arrays.asList("3456", "6543"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PeoplePhonePredicate predicate = new PeoplePhonePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Non-matching keyword
        predicate = new PeoplePhonePredicate(Arrays.asList("11111111"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }
}
