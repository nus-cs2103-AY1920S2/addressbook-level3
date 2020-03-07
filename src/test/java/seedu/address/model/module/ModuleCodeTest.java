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
    public void isValidModuleCode() { //TODO: modify this after setting valid moduleCode regex
        // null moduleCode
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid moduleCode
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("^")); // only non-alphanumeric characters
        assertFalse(ModuleCode.isValidModuleCode("run*")); // contains non-alphanumeric characters

        // valid moduleCode
        assertTrue(ModuleCode.isValidModuleCode("tutorial")); // alphabets only
        assertTrue(ModuleCode.isValidModuleCode("12345")); // numbers only
        assertTrue(ModuleCode.isValidModuleCode("cs2103 tutorial")); // alphanumeric characters
        assertTrue(ModuleCode.isValidModuleCode("tP")); // with capital letters
        assertTrue(ModuleCode.isValidModuleCode("Studying for cs2103 finals")); // long moduleCodes
    }
}
