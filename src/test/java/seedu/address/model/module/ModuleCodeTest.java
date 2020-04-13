package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        // null module code
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid module codes
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode("CS 2103T")); // contains space
        assertFalse(ModuleCode.isValidModuleCode("longModuleCode")); // long module code
        assertFalse(ModuleCode.isValidModuleCode("^")); // only non-alphanumeric characters
        assertFalse(ModuleCode.isValidModuleCode("cs2103t*")); // contains non-alphanumeric characters

        // valid module codes
        // standard NUS module codes
        assertTrue(ModuleCode.isValidModuleCode("CS1010"));
        assertTrue(ModuleCode.isValidModuleCode("CS2040S"));
        assertTrue(ModuleCode.isValidModuleCode("PCS1010"));
        assertTrue(ModuleCode.isValidModuleCode("PCS1010R"));
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
    }

}
