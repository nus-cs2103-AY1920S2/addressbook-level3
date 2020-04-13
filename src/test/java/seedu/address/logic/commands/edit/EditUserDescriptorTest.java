package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FROM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIVERSITY_BOB;
import static seedu.address.testutil.TypicalEditUserDescriptor.AMY_DESC;
import static seedu.address.testutil.TypicalEditUserDescriptor.BOB_DESC;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditUserDescriptorBuilder;

public class EditUserDescriptorTest {
    @Test
    public void isAnyFieldEdited_noFieldEdited_returnsFalse() {
        assertFalse(new EditUserDescriptorBuilder().build().isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditUserDescriptor descriptorWithSameValues = new EditUserDescriptor(AMY_DESC);
        assertEquals(AMY_DESC, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(AMY_DESC, AMY_DESC);

        // null -> returns false
        assertNotEquals(null, AMY_DESC);

        // different types -> returns false
        assertNotEquals(5, AMY_DESC);

        // different values -> returns false
        assertNotEquals(AMY_DESC, BOB_DESC);

        // different name -> returns false
        EditUserDescriptor editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withName(VALID_NAME_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different phone -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different email -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different github -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withGithub(VALID_GITHUB_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different display picture -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withDisplayPicture(VALID_DP_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different description -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withDescription(VALID_DESCRIPTION_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different university -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withUniversity(VALID_UNIVERSITY_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different major -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withMajor(VALID_MAJOR_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different from -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withFrom(VALID_FROM_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different to -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withTo(VALID_TO_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);

        // different cap -> returns false
        editedAmy = new EditUserDescriptorBuilder(AMY_DESC).withCap(VALID_CAP_BOB).build();
        assertNotEquals(AMY_DESC, editedAmy);
    }

}
