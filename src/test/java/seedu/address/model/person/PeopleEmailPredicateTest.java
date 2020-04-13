package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PeopleEmailPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("test");
        List<String> secondPredicateKeywordList = Arrays.asList("alex", "bernice");

        PeopleEmailPredicate firstPredicate = new PeopleEmailPredicate(firstPredicateKeywordList);
        PeopleEmailPredicate secondPredicate = new PeopleEmailPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PeopleEmailPredicate firstPredicateCopy = new PeopleEmailPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        PeopleEmailPredicate predicate =
                new PeopleEmailPredicate(Collections.singletonList("test@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));

        // Only one matching keyword
        predicate = new PeopleEmailPredicate(Arrays.asList("test@example.com", "test@gmail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));
    }

    @Test
    public void test_emailContainsPartialKeywords_returnsTrue() {
        // One partial keyword
        PeopleEmailPredicate predicate = new PeopleEmailPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));

        // Multiple partial keyword
        predicate = new PeopleEmailPredicate(Arrays.asList("test", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));

        // Only one matching partial keyword
        predicate = new PeopleEmailPredicate(Arrays.asList("test", "gmail"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PeopleEmailPredicate predicate = new PeopleEmailPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));

        // Non-matching keyword
        predicate = new PeopleEmailPredicate(Arrays.asList("nottest@yahoo.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));
    }
}
