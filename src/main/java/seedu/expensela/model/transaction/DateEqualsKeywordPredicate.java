package seedu.expensela.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.expensela.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Date} matches the keyword given.
 */
public class DateEqualsKeywordPredicate implements Predicate<Transaction> {
    private final List<String> keyword;

    public DateEqualsKeywordPredicate(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keyword.stream()
                .anyMatch(keyword -> StringUtil.equals(transaction.getDate().transactionDate, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateEqualsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((DateEqualsKeywordPredicate) other).keyword)); // state check
    }

}
