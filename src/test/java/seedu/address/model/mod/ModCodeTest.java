package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMods.CS2103;

import org.junit.jupiter.api.Test;

class ModCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModCode(null));
    }

    @Test
    public void constructor_invalidModCode_throwsIllegalArgumentException() {
        String invalidModCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModCode(invalidModCode));
    }

    @Test
    void isValidModCode() {
        // null modCode
        assertThrows(NullPointerException.class, () -> ModCode.isValidModCode(null));

        // blank ModCode
        assertFalse(ModCode.isValidModCode("")); // empty string
        assertFalse(ModCode.isValidModCode(" ")); // spaces only

        // missing parts
        assertFalse(ModCode.isValidModCode("1010S")); // missing department code
        assertFalse(ModCode.isValidModCode("CSS")); // missing numbers

        // other invalid parts
        assertFalse(ModCode.isValidModCode("A1010")); // department code too short
        assertFalse(ModCode.isValidModCode("AH101")); // numbers too short
    }

    @Test
    void toString_validString_true() {
        assertEquals("CS2103", CS2103.getModCode().toString());
    }

    @Test
    void hashcode_sameObject_true() {
        ModCode modCode = new ModCode("CS2103");
        assertEquals(modCode.hashCode(), "CS2103".hashCode());
    }
}
