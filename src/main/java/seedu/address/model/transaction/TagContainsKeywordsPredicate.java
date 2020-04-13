package seedu.address.model.transaction;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate extends WalletPredicate {
    public TagContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        transaction.getTag().tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
