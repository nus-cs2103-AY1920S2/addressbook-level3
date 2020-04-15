package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Product} matches any of the keywords given.
 */
public class ProductContainsKeywordPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public ProductContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(transaction.getProduct().getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((ProductContainsKeywordPredicate) other).keywords)); // state check
    }
}
