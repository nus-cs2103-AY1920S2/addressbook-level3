package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeDescriptor descriptorWithSameValues = new EditRecipeDescriptor(DESC_AMY);
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
        EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different time -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withTime(VALID_TIME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different step -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withStep(VALID_STEP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different goals -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withGoals(VALID_GOAL_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
