package seedu.address.model.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FacilitatorBuilder;

public class ModuleCodesContainKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        ModuleCodesContainKeywordPredicate firstPredicate =
                new ModuleCodesContainKeywordPredicate(firstPredicateKeyword);
        ModuleCodesContainKeywordPredicate secondPredicate =
                new ModuleCodesContainKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleCodesContainKeywordPredicate firstPredicateCopy =
                new ModuleCodesContainKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleCodesContainKeyword_returnsTrue() {
        // One module code
        ModuleCodesContainKeywordPredicate predicate = new ModuleCodesContainKeywordPredicate("CS2103T");
        assertTrue(predicate.test(new FacilitatorBuilder().withModuleCodes("CS2103T").build()));

        // Multiple module codes
        predicate = new ModuleCodesContainKeywordPredicate("CS2103T");
        assertTrue(predicate.test(new FacilitatorBuilder().withModuleCodes("CS2103T", "CS2101").build()));

        // Mixed-case keywords
        predicate = new ModuleCodesContainKeywordPredicate("Cs2103t");
        assertTrue(predicate.test(new FacilitatorBuilder().withModuleCodes("CS2103T", "CS2101").build()));
    }

    @Test
    public void test_moduleCodesDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        ModuleCodesContainKeywordPredicate predicate = new ModuleCodesContainKeywordPredicate("CS2101");
        assertFalse(predicate.test(new FacilitatorBuilder().withModuleCodes("CS2103T").build()));

        // Keywords match name and office, but does not match module code
        predicate = new ModuleCodesContainKeywordPredicate("CS2103T");
        assertFalse(predicate.test(new FacilitatorBuilder().withName("CS2103T").withOffice("CS2103T")
                .withModuleCodes("CS2101").build()));
    }
}
