package seedu.address.logic.commands.wallet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BOB;
import static seedu.address.testutil.TypicalWallet.VALID_AMOUNT_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_DATE_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_TAG_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_TAG_TA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        WalletEditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESC_BOB)
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(LocalDate.parse(VALID_DATE, DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                .withTag(VALID_TAG_TA).build();
        WalletEditCommand.EditTransactionDescriptor descriptorCopy = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESC_BOB)
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(LocalDate.parse(VALID_DATE, DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                .withTag(VALID_TAG_TA).build();
        WalletEditCommand.EditTransactionDescriptor anotherDescriptor = new EditTransactionDescriptorBuilder()
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(LocalDate.parse(VALID_DATE, DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                .withTag(VALID_TAG_TA).build();

        // same values -> returns true
        assertTrue(descriptor.equals(descriptorCopy));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        assertFalse(descriptor.equals(anotherDescriptor));

        // different name -> returns false
        WalletEditCommand.EditTransactionDescriptor
                editedDescriptor =
                new EditTransactionDescriptorBuilder(descriptor).withDescription(VALID_DESC_AMY).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different amount -> returns false
        editedDescriptor = new EditTransactionDescriptorBuilder(descriptor)
                .withAmount(Double.parseDouble(VALID_AMOUNT_DUCK)).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different date -> returns false
        editedDescriptor = new EditTransactionDescriptorBuilder(descriptor)
                .withDate(LocalDate.parse(VALID_DATE_DUCK, DateTimeFormatter.ofPattern("dd/MM/uuuu"))).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different tag -> returns false
        editedDescriptor =
                new EditTransactionDescriptorBuilder(descriptor).withTag(VALID_TAG_DUCK).build();
        assertFalse(descriptor.equals(editedDescriptor));
    }
}
