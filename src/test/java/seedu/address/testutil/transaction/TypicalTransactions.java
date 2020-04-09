package seedu.address.testutil.transaction;

import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.customer.TypicalCustomers.BENSON;
import static seedu.address.testutil.product.TypicalProducts.ABACUS;
import static seedu.address.testutil.product.TypicalProducts.BAG;
import static seedu.address.testutil.product.TypicalProducts.BOOK;

import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction ALICE_BUY_ONE_BAG_MARCH_FIRST = new TransactionBuilder().withCustomer(ALICE)
            .withProduct(BAG).withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1)
            .withDescription("promotion").build();
    public static final Transaction ALICE_BUY_TWO_BAG_MARCH_FIRST = new TransactionBuilder().withCustomer(ALICE)
            .withProduct(BAG).withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(2)
            .withDescription("promotion").build();
    public static final Transaction ALICE_BUY_ONE_BOOK_MARCH_FIRST = new TransactionBuilder().withCustomer(ALICE)
            .withProduct(BOOK).withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1)
            .withDescription("normal price").build();
    public static final Transaction ALICE_BUY_ONE_BAG_MARCH_SECOND = new TransactionBuilder().withCustomer(ALICE)
            .withProduct(BAG).withDateTime("2020-03-02 10:00").withMoney(20).withQuantity(1)
            .withDescription("promotion").build();
    public static final Transaction BENSON_BUY_ONE_ABACUS_MARCH_FIRST = new TransactionBuilder().withCustomer(BENSON)
            .withProduct(ABACUS).withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1)
            .withDescription("normal price").build();
    public static final Transaction BENSON_BUY_ONE_BAG_MARCH_FIRST = new TransactionBuilder().withCustomer(BENSON)
            .withProduct(BAG).withDateTime("2020-03-01 10:00").withMoney(20).withQuantity(1)
            .withDescription("promotion").build();
}
