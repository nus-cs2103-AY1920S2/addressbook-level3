package seedu.address.logic.commands.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditFacilitatorDescriptorBuilder;

public class EditFacilitatorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        FacilEditCommand.EditFacilitatorDescriptor descriptorWithSameValues =
                new FacilEditCommand.EditFacilitatorDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        FacilEditCommand.EditFacilitatorDescriptor editedAmy = new EditFacilitatorDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditFacilitatorDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditFacilitatorDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different office -> returns false
        editedAmy = new EditFacilitatorDescriptorBuilder(DESC_AMY).withOffice(VALID_OFFICE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different module codes -> returns false
        editedAmy = new EditFacilitatorDescriptorBuilder(DESC_AMY).withModuleCodes(VALID_MODULE_CODE_CS2103T).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
