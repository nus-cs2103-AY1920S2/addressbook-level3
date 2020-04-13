package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction} matches any of the keywords given.
 */
public abstract class WalletPredicate implements Predicate<Transaction> {
    protected final List<String> keywords;

    public WalletPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WalletPredicate // instanceof handles nulls
                && keywords.equals(((WalletPredicate) other).keywords)); // state check
    }
}
