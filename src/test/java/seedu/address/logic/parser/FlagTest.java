package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FlagTest {
    private static String testFlag = "test";
    @Test
    public void initFlag_returnSameFlag_success() {
        Flag expectedFlag = new Flag(testFlag);
        assertEquals(new Flag(testFlag), expectedFlag);
    }

    @Test
    public void checkHashCode_returnSameHash_success() {
        Flag expectedFlag = new Flag(testFlag);
        assertEquals(new Flag(testFlag).hashCode(), expectedFlag.hashCode());
    }

    @Test
    public void checkIsPrefixClass_ReturnFalse_Success() {
        Flag checkFlag = new Flag(testFlag);
        assertFalse(checkFlag.equals(Prefix.class));
    }

    @Test
    public void checkSameFlagObject_ReturnTrue_Success() {
        Flag checkFlag = new Flag(testFlag);
        assertTrue(checkFlag.equals(checkFlag));
    }
}
