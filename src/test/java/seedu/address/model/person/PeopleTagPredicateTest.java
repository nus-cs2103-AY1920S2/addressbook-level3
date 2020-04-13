package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PeopleTagPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friend");
        List<String> secondPredicateKeywordList = Arrays.asList("colleague", "family");

        PeopleTagPredicate firstPredicate = new PeopleTagPredicate(firstPredicateKeywordList);
        PeopleTagPredicate secondPredicate = new PeopleTagPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PeopleTagPredicate firstPredicateCopy = new PeopleTagPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        PeopleTagPredicate predicate = new PeopleTagPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Only one matching keyword
        predicate = new PeopleTagPredicate(Arrays.asList("friend", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // multiple keyword matched
        predicate = new PeopleTagPredicate(Arrays.asList("friend", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "family").build()));
    }

    @Test
    public void test_tagContainsPartialKeywords_returnsTrue() {
        // One partial keyword
        PeopleTagPredicate predicate = new PeopleTagPredicate(Collections.singletonList("fri"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Multiple partial keyword
        predicate = new PeopleTagPredicate(Arrays.asList("end", "amil"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "family").build()));

        // Only one matching partial keyword
        predicate = new PeopleTagPredicate(Arrays.asList("frie", "fam"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PeopleTagPredicate predicate = new PeopleTagPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new PeopleTagPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));
    }
}
