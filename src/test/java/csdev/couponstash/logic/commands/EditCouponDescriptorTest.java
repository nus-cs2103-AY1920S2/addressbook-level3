package csdev.couponstash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.EditCommand.EditCouponDescriptor;
import csdev.couponstash.testutil.EditCouponDescriptorBuilder;

public class EditCouponDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCouponDescriptor descriptorWithSameValues = new EditCouponDescriptor(CommandTestUtil.DESC_AMY);
        assertTrue(CommandTestUtil.DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_AMY));

        // null -> returns false
        assertFalse(CommandTestUtil.DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(CommandTestUtil.DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_BOB));

        // different name -> returns false
        EditCouponDescriptor editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withPromoCode(CommandTestUtil.VALID_PROMO_CODE_BOB).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different limit -> returns false
        editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withLimit(CommandTestUtil.VALID_LIMIT_BOB).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different start date -> returns false
        editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withStartDate(CommandTestUtil.VALID_EXPIRY_DATE_BOB).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different expiry date -> returns false
        editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withExpiryDate(CommandTestUtil.VALID_EXPIRY_DATE_BOB).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCouponDescriptorBuilder(CommandTestUtil.DESC_AMY)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));
    }
}
