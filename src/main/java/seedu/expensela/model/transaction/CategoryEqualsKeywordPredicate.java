package seedu.expensela.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.expensela.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Category} matches the keyword given.
 */
public class CategoryEqualsKeywordPredicate implements Predicate<Transaction> {
    private final List<String> keyword;

    public CategoryEqualsKeywordPredicate(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Transaction transaction) {
        if (keyword.get(0).equals("ALL")) {
            return true;
        }
        return keyword.stream()
                .anyMatch(keyword -> StringUtil.equals(transaction.getCategory().transactionCategory, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryEqualsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((CategoryEqualsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public String toString() {
        return this.keyword.get(0);
    }

}
