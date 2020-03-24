package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.EditCommand.EditTransactionDescriptor;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTransactionDescriptor descriptorWithSameValues = new EditTransactionDescriptor(DESC_PIZZA);
        assertTrue(DESC_PIZZA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PIZZA.equals(DESC_PIZZA));

        // null -> returns false
        assertFalse(DESC_PIZZA.equals(null));

        // different types -> returns false
        assertFalse(DESC_PIZZA.equals(5));

        // different values -> returns false
        assertFalse(DESC_PIZZA.equals(DESC_AIRPODS));

        // different name -> returns false
        EditTransactionDescriptor editedAmy = new EditTransactionDescriptorBuilder(DESC_PIZZA).withName(VALID_NAME_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_PIZZA).withPhone(VALID_AMOUNT_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_PIZZA).build();
        assertFalse(DESC_PIZZA.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_PIZZA).withAddress(VALID_REMARK_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTransactionDescriptorBuilder(DESC_PIZZA).build();
        assertFalse(DESC_PIZZA.equals(editedAmy));
    }
}
