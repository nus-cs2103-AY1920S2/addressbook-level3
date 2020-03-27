package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.testutil.TypicalTransactions.AIRPODS;
import static seedu.expensela.testutil.TypicalTransactions.PIZZA;

import org.junit.jupiter.api.Test;

import seedu.expensela.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void isSameTransaction() {
        // same object -> returns true
        assertTrue(PIZZA.isSameTransaction(PIZZA));

        // null -> returns false
        assertFalse(PIZZA.isSameTransaction(null));

        // different amount -> returns false
        Transaction editedPizza = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false).build();
        assertFalse(PIZZA.isSameTransaction(editedPizza));

        // different name -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withName(VALID_NAME_AIRPODS).build();
        assertFalse(PIZZA.isSameTransaction(editedPizza));

        // same name, same amount, same category, different remark -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS)
                .build();
        assertFalse(PIZZA.isSameTransaction(editedPizza));

        // same name, same category, different amount, different remark -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false)
                .withRemark(VALID_REMARK_AIRPODS)
                .build();
        assertFalse(PIZZA.isSameTransaction(editedPizza));

        // same name, same amount, same remark, different category -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withCategory(VALID_CATEGORY_SHOPPING).build();
        assertFalse(PIZZA.isSameTransaction(editedPizza));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Transaction aliceCopy = new TransactionBuilder(PIZZA).build();
        assertTrue(PIZZA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PIZZA.equals(PIZZA));

        // null -> returns false
        assertFalse(PIZZA.equals(null));

        // different type -> returns false
        assertFalse(PIZZA.equals(5));

        // different transaction -> returns false
        assertFalse(PIZZA.equals(AIRPODS));

        // different name -> returns false
        Transaction editedPizza = new TransactionBuilder(PIZZA).withName(VALID_NAME_AIRPODS).build();
        assertFalse(PIZZA.equals(editedPizza));

        // different amount -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withAmount(VALID_AMOUNT_AIRPODS, false).build();
        assertFalse(PIZZA.equals(editedPizza));

        // different remark -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withRemark(VALID_REMARK_AIRPODS).build();
        assertFalse(PIZZA.equals(editedPizza));

        // different category -> returns false
        editedPizza = new TransactionBuilder(PIZZA).withCategory(VALID_CATEGORY_SHOPPING).build();
        assertFalse(PIZZA.equals(editedPizza));
    }
}
