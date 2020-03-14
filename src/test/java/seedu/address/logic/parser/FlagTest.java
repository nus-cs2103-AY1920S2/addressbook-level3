package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}