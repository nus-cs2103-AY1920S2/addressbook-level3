package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BlockTreeItemTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BlockTreeItemImpl(null, null));
    }

}
