package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.transaction.TypicalTransactions.ALICE_BUY_ONE_BAG_MARCH_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.Customer;
import seedu.address.testutil.customer.CustomerBuilder;
import seedu.address.testutil.transaction.TransactionBuilder;

public class CustomerContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Alice");
        List<String> secondPredicateKeywordList = Arrays.asList("Alice", "Bob");

        CustomerContainsKeywordPredicate firstPredicate = new
                CustomerContainsKeywordPredicate(firstPredicateKeywordList);
        CustomerContainsKeywordPredicate secondPredicate = new
                CustomerContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CustomerContainsKeywordPredicate firstPredicateCopy = new
                CustomerContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_customerContainsKeyword_returnsTrue() {
        //one keyword
        CustomerContainsKeywordPredicate predicate = new
                CustomerContainsKeywordPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(ALICE_BUY_ONE_BAG_MARCH_FIRST));

        //multiple keyword
        predicate = new CustomerContainsKeywordPredicate(Arrays.asList("Alice", "Bob"));
        Customer editedAlice = new CustomerBuilder(ALICE).withName("Alice Bob").build();
        assertTrue(predicate.test(new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withCustomer(editedAlice).build()));

        //only one matching keyword
        predicate = new CustomerContainsKeywordPredicate(Arrays.asList("Alice", "Andreas"));
        assertTrue(predicate.test(new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withCustomer(editedAlice).build()));

        //mixed case keywords
        predicate = new CustomerContainsKeywordPredicate(Arrays.asList("aLicE", "bOb"));
        assertTrue(predicate.test(new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withCustomer(editedAlice).build()));
    }

    @Test
    public void test_customerContainsKeyword_returnsFalse() {
        // no keyword
        CustomerContainsKeywordPredicate predicate =
                new CustomerContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(ALICE_BUY_ONE_BAG_MARCH_FIRST));

        //no matching keyword
        predicate = new CustomerContainsKeywordPredicate(Arrays.asList("random", "string"));
        assertFalse(predicate.test(ALICE_BUY_ONE_BAG_MARCH_FIRST));

        //keywords matching other attributes rather than customer name.
        predicate = new CustomerContainsKeywordPredicate(Arrays.asList("1", "2020-03-01", "10:00", "20", "promotion"));
        assertFalse(predicate.test(new TransactionBuilder(ALICE_BUY_ONE_BAG_MARCH_FIRST)
                .withQuantity(1).withDateTime("2020-03-01 10:00").withMoney(20).withDescription("promotion").build()));
    }
}
