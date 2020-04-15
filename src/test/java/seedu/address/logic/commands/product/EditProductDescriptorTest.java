package seedu.address.logic.commands.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BAG;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_WATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALES_WATCH;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.testutil.product.EditProductDescriptorBuilder;

public class EditProductDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditProductDescriptor descriptorWithSameValues = new EditProductDescriptor(DESC_BAG);
        assertTrue(DESC_BAG.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BAG.equals(DESC_BAG));

        // null -> returns false
        assertFalse(DESC_BAG.equals(null));

        // different types -> returns false
        assertFalse(DESC_BAG.equals(5));

        // different values -> returns false
        assertFalse(DESC_BAG.equals(DESC_WATCH));

        // different description -> returns false
        EditProductDescriptor editedAmy = new
                EditProductDescriptorBuilder(DESC_BAG).withDescription(VALID_DESCRIPTION_WATCH).build();
        assertFalse(DESC_BAG.equals(editedAmy));

        // different price -> returns false
        editedAmy = new EditProductDescriptorBuilder(DESC_BAG).withPrice(VALID_PRICE_WATCH).build();
        assertFalse(DESC_BAG.equals(editedAmy));

        // different quantity -> returns false
        editedAmy = new EditProductDescriptorBuilder(DESC_BAG).withQuantity(VALID_QUANTITY_WATCH).build();
        assertFalse(DESC_BAG.equals(editedAmy));

        // different sales -> returns false
        editedAmy = new EditProductDescriptorBuilder(DESC_BAG).withSales(VALID_SALES_WATCH).build();
        assertFalse(DESC_BAG.equals(editedAmy));
    }
}
