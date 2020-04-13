package seedu.address.model.transaction;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate extends WalletPredicate {

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        transaction.getDescription().description, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
