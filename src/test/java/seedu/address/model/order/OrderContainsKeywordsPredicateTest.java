package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrderContainsKeywordsPredicate firstPredicate = new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        OrderContainsKeywordsPredicate secondPredicate = new OrderContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderContainsKeywordsPredicate firstPredicateCopy =
            new OrderContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywordsGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_addressContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Lorong 10 Geylang st 10").build()));

        // Multiple keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("street", "81", "Yishun"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Yishun street 81").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon", "Tampines"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("Tampines 10").build()));

        // Mixed-case keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("cHuAn", "GeYLaNg"));
        assertTrue(predicate.test(new OrderBuilder().withAddress("geylang lorong").build()));
    }

    @Test
    public void test_phoneContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("97555838"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("97555838").build()));

        // Only one matching keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999", "98765432"));
        assertTrue(predicate.test(new OrderBuilder().withPhone("999").build()));
    }

    @Test
    public void test_timeStampContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match date and time
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("2020-02-02"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-02-02 1500").build()));

        // Only one matching keyword of date and time
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "2020-01-02"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-01-02 1500").build()));

        // Only matching time keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-01-01", "1500", "2020-01-02", "1400"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-05-05 1500").build()));

        // Only matching dates keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-05-05", "1300", "2020-01-02", "1400"));
        assertTrue(predicate.test(new OrderBuilder().withTimeStamp("2020-05-05 1200").build()));
    }

    @Test
    public void test_warehouseContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match warehouse address
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "#02-30", "S608831"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("5 Toh Guan Rd E, #02-30 S608831").build()));

        // Only one full matching keyword of warehouse address
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("5", "Toh", "Guan", "Rd", "E", "Geylang"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Geylang").build()));

        // Any one word matches the given warehouse address
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "New", "Town"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun Old").build()));

        // Only matches the postal code
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("123456", "760844"));
        assertTrue(predicate.test(new OrderBuilder().withWarehouse("Yishun st 71 blk 777 760844").build()));
    }

    @Test
    public void test_commentsContainsKeywordGeneralSearch_returnsTrue() {
        // One keyword full match comment given
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Please", "say", "hi", "to", "me"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Please say hi to me").build()));

        // Only one full matching keyword of comment given
        predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("5", "hi", "S608831",
                "Put", "in", "at", "my", "shoe", "rack"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Put in at my shoe rack").build()));

        // Any one word matches the given comment
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Hi"));
        assertTrue(predicate.test(new OrderBuilder().withComment("Say hi to me when you're here!").build()));
    }

    @Test
    public void test_emptyKeywordGivenInGeneralSearch_returnFalse() {
        // Empty keyword given
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OrderBuilder().build()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Permutations of keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Aeicl", "oBb"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Substring of keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alic", "Bo"));
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching phone number
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Permutations of phone number
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("978", "654"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("879").build()));

        // Substring of phone number
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("999", "888"));
        assertFalse(predicate.test(new OrderBuilder().withPhone("99988899").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching address
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Yishun", "st", "81"));
        assertFalse(predicate.test(new OrderBuilder().withName("Jurong West 36").build()));
    }

    @Test
    public void test_timeStampDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching timestamp keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("2020", "1345", "01"));
        assertFalse(predicate.test(new OrderBuilder().withTimeStamp("2040-02-03 1000").build()));
    }

    @Test
    public void test_warehouseDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching warehouse address keyword
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #02-30 S608831").build()));

        // Missing S in postal code keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "#05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #02-30 S134544").build()));

        // Missing # in postal code keyword
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Toh", "134544", "05-11"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang Street 81, #05-11 S134544").build()));
    }

    @Test
    public void test_commentsDoesNotContainKeywordsGeneralSearch_returnsFalse() {
        // Non-matching warehouse address keyword
        OrderContainsKeywordsPredicate predicate = new OrderContainsKeywordsPredicate(Arrays.asList("hi"));
        assertFalse(predicate.test(new OrderBuilder().withComment("bye").build()));

        // Substring comments do not match
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("hiiiiiiiiiiiiiiiiii", "hi"));
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("hiii").build()));
    }

    @Test
    public void test_nameContainsKeywordsSpecificSearch_returnTrue() {
        // Matching keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"), true, false,
                false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Multiple matching name keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), true, false,
            false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Mixed-case name keywords
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("ALiCe"), true,
            false, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_addressContainsKeywordsSpecificSearch_returnTrue() {
        // Matching keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), false, true,
                false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang").build()));

        // Multiple matching name keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("Geylang", "street"), false,
            true, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang street").build()));

        // Mixed-case name keywords
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("geYlAnG"), false,
            true, false, false, false, false);
        assertTrue(predicate.test(new OrderBuilder().withAddress("Geylang Street").build()));
    }

    @Test
    public void test_phoneContainsKeywordsSpecificSearch_returnTrue() {
        // Matching phone keywords
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"), false, false,
                false, true, false, false);
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Only one single matching phone keywords
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("12345678", "111111"), false,
            false, false, true, false, false);
        assertTrue(predicate.test(new OrderBuilder().withPhone("12345678").build()));
    }

    // TODO
    // More test case for false and warehouse, comment, timestamp, cod and transaction id

    @Test void test_fieldsOtherThanSpecifiedFieldContainsKeywordSpecificSearch_returnFalse() {
        // Matching keywords in name fields but did not search name prefix
        OrderContainsKeywordsPredicate predicate =
            new OrderContainsKeywordsPredicate(Collections.singletonList("Alice"), false, true,
                true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withName("Alice Bob").build()));

        // Matching keywords in address fields but did not search address prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), true,
            false, true, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withAddress("Geylang").build()));

        // Matching keywords in comment fields but did not search comment prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("hi"), true,
            true, false, true, true, true);
        assertFalse(predicate.test(new OrderBuilder().withComment("hi").build()));

        // Matching keywords in phone fields but did not search phone prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("12345678"), true,
            true, true, false, true, true);
        assertFalse(predicate.test(new OrderBuilder().withPhone("12345678").build()));

        // Matching keywords in timestamp fields but did not search timestamp prefix
        predicate = new OrderContainsKeywordsPredicate(Arrays.asList("2020-02-02", "1500"), true,
            true, true, true, false, true);
        assertFalse(predicate.test(new OrderBuilder().withTimeStamp("2020-02-02 1500").build()));

        // Matching keywords in warehouse address fields but did not search warehouse address prefix
        predicate = new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), true,
            true, true, true, true, false);
        assertFalse(predicate.test(new OrderBuilder().withWarehouse("Geylang").build()));
    }
}
