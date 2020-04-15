package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.transaction.TransactionFactoryBuilder;

public class TransactionFactoryTest {

    @Test
    public void createTransaction() throws CommandException {
        // null model -> throws null pointer exception
        TransactionFactory defaultFactory = new TransactionFactoryBuilder().build();
        assertThrows(NullPointerException.class, () -> defaultFactory.createTransaction(null));
    }

    @Test
    public void equals() {
        // same value -> returns true
        TransactionFactory defaultFactory = new TransactionFactoryBuilder().build();
        TransactionFactory defaultFactoryCopy = new TransactionFactoryBuilder(defaultFactory).build();
        assertTrue(defaultFactory.equals(defaultFactoryCopy));

        // same object -> returns true
        assertTrue(defaultFactory.equals(defaultFactory));

        // null => returns false
        assertFalse(defaultFactory.equals(null));

        // different customer index -> returns false
        TransactionFactory defaultFactoryWithCustomerTwo = new TransactionFactoryBuilder(defaultFactory)
                .withCustomerIndex(Index.fromOneBased(2)).build();
        assertFalse(defaultFactory.equals(defaultFactoryWithCustomerTwo));

        // different product index -> returns false
        TransactionFactory defaultFactoryWithProductTwo = new TransactionFactoryBuilder(defaultFactory)
                .withProductIndex(Index.fromOneBased(2)).build();
        assertFalse(defaultFactory.equals(defaultFactoryWithProductTwo));

        // different dateTime -> returns false
        TransactionFactory defaultFactoryWithDiffDateTime = new TransactionFactoryBuilder(defaultFactory)
                .withDateTime("2020-03-03 10:00").build();
        assertFalse(defaultFactory.equals(defaultFactoryWithDiffDateTime));

        // different quantity -> returns false
        TransactionFactory defaultFactoryWithDiffQuantity = new TransactionFactoryBuilder(defaultFactory)
                .withQuantity(2).build();
        assertFalse(defaultFactory.equals(defaultFactoryWithDiffQuantity));

        // different money -> returns false
        TransactionFactory defaultFactoryWithDiffMoney = new TransactionFactoryBuilder(defaultFactory)
                .withMoney(2).build();
        assertFalse(defaultFactory.equals(defaultFactoryWithDiffMoney));

        // different description -> returns false
        TransactionFactory defaultFactoryWithDiffDescription = new TransactionFactoryBuilder(defaultFactory)
                .withDescription("normal price").build();
        assertFalse(defaultFactory.equals(defaultFactoryWithDiffDescription));

    }
}
