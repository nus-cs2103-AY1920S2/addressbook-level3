package seedu.address.model.transaction;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction}'s {@code Product} matches the id given.
 */
public class ProductIdEqualsPredicate implements Predicate<Transaction> {
    private final UUID id;

    public ProductIdEqualsPredicate(UUID id) {
        this.id = id;
    }

    @Override
    public boolean test(Transaction transaction) {
        return id.equals(transaction.getProductId());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductIdEqualsPredicate // instanceof handles nulls
                && id.equals(((ProductIdEqualsPredicate) other).id)); // state check
    }
}
