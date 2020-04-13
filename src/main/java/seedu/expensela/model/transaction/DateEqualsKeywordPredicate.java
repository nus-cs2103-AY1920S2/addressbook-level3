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

    /**
     * Tests that a {@code Transaction}'s {@code Date}'s year and month matches the
     * year and month of the given keyword.
     * @param transaction
     * @return
     */

    @Override
    public boolean test(Transaction transaction) {
        String transactionDateString = transaction.getDate().transactionDate.toString();
        if (keyword.get(0).equals("ALL")) {
            return true;
        }
        return keyword.stream()
                .anyMatch(keyword -> StringUtil.equals(transactionDateString.split("-")[0] + "-"
                        + transactionDateString.split("-")[1], keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateEqualsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((DateEqualsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public String toString() {
        return this.keyword.get(0);
    }

}
