package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class ProductContainsKeywordPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public ProductContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getProduct(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((ProductContainsKeywordPredicate) other).keywords)); // state check
    }
}
