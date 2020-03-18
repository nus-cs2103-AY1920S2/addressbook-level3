package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

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
                || (other instanceof DateTimeEqualsPredicate // instanceof handles nulls
                && predicates.equals(((JointTransactionPredicate) other).predicates)); // state check
    }
}
