package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void constructor_onlyWhitespaces_returnsTrue() {
        assertEquals(new Body("   ").getText(), "");
    }
}
