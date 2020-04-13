package seedu.address.testutil.transaction;

import seedu.address.commons.core.index.Index;
import seedu.address.model.transaction.TransactionFactory;

/**
 * A utility class containing a list of {@code TransactionFactory} objects to be used in tests.
 */
public class TypicalTransactionFactories {

    public static final TransactionFactory ONE_ONE_MARCH_FIRST_TWENTY_ONE = new TransactionFactoryBuilder()
            .withCustomerIndex(Index.fromOneBased(1)).withProductIndex(Index.fromOneBased(1))
            .withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1).withDescription("under discount").build();
    public static final TransactionFactory ONE_ONE_MARCH_FIRST_TWENTY_TWO = new TransactionFactoryBuilder()
            .withCustomerIndex(Index.fromOneBased(1)).withProductIndex(Index.fromOneBased(1))
            .withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(2).withDescription("promotion").build();
    public static final TransactionFactory ONE_TWO_MARCH_FIRST_TWENTY_ONE = new TransactionFactoryBuilder()
            .withCustomerIndex(Index.fromOneBased(1)).withProductIndex(Index.fromOneBased(2))
            .withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1).withDescription("normal price").build();
}
