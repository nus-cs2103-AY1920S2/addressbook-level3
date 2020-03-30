package seedu.address.model.order.returnorder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

class ReturnOrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ArgumentMultimap argMultimapFirst = new ArgumentMultimap();
        ArgumentMultimap argumentMultimapSecond = new ArgumentMultimap();
        argumentMultimapSecond.put(new Prefix(""), "first second");

        // constructor with no argumentmultimap passed in.
        ReturnOrderContainsKeywordsPredicate firstPredicate =
            new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList);
        ReturnOrderContainsKeywordsPredicate secondPredicate =
            new ReturnOrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // constructor with argumentmultimap passed in.
        ReturnOrderContainsKeywordsPredicate thirdPredicate =
            new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList, argMultimapFirst);
        ReturnOrderContainsKeywordsPredicate fourthPredicate =
            new ReturnOrderContainsKeywordsPredicate(secondPredicateKeywordList, argumentMultimapSecond);

        // same objects return true.
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value return true.
        assertTrue(firstPredicate.equals(new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList)));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        //======================================= Overload constructor ================================================
        // same objects returns true
        assertTrue(thirdPredicate.equals(thirdPredicate));

        // same value return true
        assertTrue(thirdPredicate
            .equals(new ReturnOrderContainsKeywordsPredicate(firstPredicateKeywordList, argMultimapFirst)));


    }

    @Test
    public void testEquals() {
    }
}