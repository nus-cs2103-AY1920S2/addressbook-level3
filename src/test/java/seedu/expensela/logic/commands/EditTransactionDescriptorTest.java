package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;

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
        EditTransactionDescriptor editedPizza = new EditTransactionDescriptorBuilder(DESC_PIZZA)
                .withName(VALID_NAME_AIRPODS)
                .build();
        assertFalse(DESC_PIZZA.equals(editedPizza));

        // different amount -> returns false
        editedPizza = new EditTransactionDescriptorBuilder(DESC_PIZZA).withAmount(VALID_AMOUNT_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedPizza));

        // different date -> returns false
        editedPizza = new EditTransactionDescriptorBuilder(DESC_PIZZA).withDate(VALID_DATE_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedPizza));

        // different remark -> returns false
        editedPizza = new EditTransactionDescriptorBuilder(DESC_PIZZA).withRemark(VALID_REMARK_AIRPODS).build();
        assertFalse(DESC_PIZZA.equals(editedPizza));

        // different category -> returns false
        editedPizza = new EditTransactionDescriptorBuilder(DESC_PIZZA).withCategory(VALID_CATEGORY_SHOPPING).build();
        assertFalse(DESC_PIZZA.equals(editedPizza));
    }
}
