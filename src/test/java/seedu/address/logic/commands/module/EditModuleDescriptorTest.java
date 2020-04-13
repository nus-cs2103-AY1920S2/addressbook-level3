package seedu.address.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ModuleEditCommand.EditModuleDescriptor descriptorWithSameValues =
                new ModuleEditCommand.EditModuleDescriptor(DESC_CS1101S);
        assertTrue(DESC_CS1101S.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS1101S.equals(DESC_CS1101S));

        // null -> returns false
        assertFalse(DESC_CS1101S.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS1101S.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS1101S.equals(DESC_CS2101));

        // different module code -> returns false
        ModuleEditCommand.EditModuleDescriptor editedCs1101s = new EditModuleDescriptorBuilder(DESC_CS1101S)
                .withModuleCode(VALID_MODULE_CODE_CS2101).build();
        assertFalse(DESC_AMY.equals(editedCs1101s));

        // different description -> returns false
        editedCs1101s = new EditModuleDescriptorBuilder(DESC_CS1101S).withDescription(VALID_DESCRIPTION_CS2101).build();
        assertFalse(DESC_AMY.equals(editedCs1101s));
    }

}
