package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS1231;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import nasa.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditModuleDescriptor descriptorWithSameValues = new EditModuleDescriptor(DESC_CS2030);
        assertTrue(DESC_CS2030.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2030.equals(DESC_CS2030));

        // null -> returns false
        assertFalse(DESC_CS2030.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2030.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2030.equals(DESC_CS1231));

        // different module name -> returns false
        EditModuleDescriptor editedExam = new EditModuleDescriptorBuilder(DESC_CS2030)
                .withModuleName(VALID_MODULE_NAME_CS1231).build();
        assertFalse(DESC_CS2030.equals(editedExam));

        // different module code -> returns false
        editedExam = new EditModuleDescriptorBuilder(DESC_CS2030).withModuleCode(VALID_MODULE_CODE_CS1231).build();
        assertFalse(DESC_CS2030.equals(editedExam));

    }
}
