package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Transactions}'s {@code Customer} matches any of the keywords given.
 */
public class CustomerContainsKeywordPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public CustomerContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(transaction.getCustomer().getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((CustomerContainsKeywordPredicate) other).keywords)); // state check
    }
}
