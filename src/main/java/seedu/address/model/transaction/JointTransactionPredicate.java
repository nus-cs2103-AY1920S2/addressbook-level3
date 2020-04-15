package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Transaction}'s attributes fulfill the given properties.
 */
public class JointTransactionPredicate implements Predicate<Transaction> {
    private final List<Predicate<Transaction>> predicates;

    public JointTransactionPredicate(List<Predicate<Transaction>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Transaction transaction) {
        return predicates.stream()
                .allMatch(predicate -> predicate.test(transaction));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JointTransactionPredicate // instanceof handles nulls
                && predicates.equals(((JointTransactionPredicate) other).predicates)); // state check
    }
}
