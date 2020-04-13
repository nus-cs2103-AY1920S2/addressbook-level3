package nasa.model.module;

import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // null moduleCode
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid moduleCode
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("^")); // only non-alphanumeric characters
        assertFalse(ModuleCode.isValidModuleCode("run*")); // contains non-alphanumeric characters
        assertFalse(ModuleCode.isValidModuleCode("cs2103 finals")); // module code with whitespace
        assertFalse(ModuleCode.isValidModuleCode("stringchars")); // 11 characters

        // valid moduleCode
        assertTrue(ModuleCode.isValidModuleCode("tutorial")); // alphabets only
        assertTrue(ModuleCode.isValidModuleCode("12345")); // numbers only
        assertTrue(ModuleCode.isValidModuleCode("t")); // 1 alphanumeric letter
        assertTrue(ModuleCode.isValidModuleCode("CS2103TCS2")); // 10 alphanumeric characters
        assertTrue(ModuleCode.isValidModuleCode("tP")); // with capital letters
    }
}
