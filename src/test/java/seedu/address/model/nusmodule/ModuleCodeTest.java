package seedu.address.model.nusmodule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModuleCodeTest {

    @Test
    void isValidModuleCode() {
        assertTrue(ModuleCode.isValidModuleCode("CS2030"));
        assertTrue(ModuleCode.isValidModuleCode("CS2040"));
        assertTrue(ModuleCode.isValidModuleCode("CS1101S"));
        assertTrue(ModuleCode.isValidModuleCode("ST3131"));
        assertFalse(ModuleCode.isValidModuleCode(""));
        assertFalse(ModuleCode.isValidModuleCode("casfas"));
        assertFalse(ModuleCode.isValidModuleCode("12312rd"));
        assertFalse(ModuleCode.isValidModuleCode("cs 1101s"));
    }

    @Test
    void testToString() {
        ModuleCode moduleCode = new ModuleCode("cs2030");
        assertEquals(moduleCode.toString(), "cs2030");
    }

    @Test
    void testEquals() {
        ModuleCode moduleCode = new ModuleCode("cs2030");

        // same values -> returns true
        assertTrue(moduleCode.equals(new ModuleCode("cs2030")));

        // same object -> returns true
        assertTrue(moduleCode.equals(moduleCode));

        // null -> returns false
        assertFalse(moduleCode.equals(null));

        // different types -> returns false
        assertFalse(moduleCode.equals(0.5f));

        // different module code value -> returns false
        assertFalse(moduleCode.equals(new ModuleCode("cs2040")));

    }
}
