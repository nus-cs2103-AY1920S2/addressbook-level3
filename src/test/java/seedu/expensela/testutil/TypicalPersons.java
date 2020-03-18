package seedu.expensela.testutil;

import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final Transaction ALICE = new TransactionBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .build();
    public static final Transaction BENSON = new TransactionBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .build();
    public static final Transaction CARL = new TransactionBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").build();
    public static final Transaction DANIEL = new TransactionBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street").build();
    public static final Transaction ELLE = new TransactionBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").build();
    public static final Transaction FIONA = new TransactionBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").build();
    public static final Transaction GEORGE = new TransactionBuilder().withName("George Best").withPhone("9482442")
            .withAddress("4th street").build();

    // Manually added
    public static final Transaction HOON = new TransactionBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").build();
    public static final Transaction IDA = new TransactionBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").build();

    // Manually added - Transaction's details found in {@code CommandTestUtil}
    public static final Transaction AMY = new TransactionBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).build();
    public static final Transaction BOB = new TransactionBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code ExpenseLa} with all the typical transactions.
     */
    public static ExpenseLa getTypicalExpenseLa() {
        ExpenseLa ab = new ExpenseLa();
        for (Transaction transaction : getTypicalTransactions()) {
            ab.addTransaction(transaction);
        }
        return ab;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
