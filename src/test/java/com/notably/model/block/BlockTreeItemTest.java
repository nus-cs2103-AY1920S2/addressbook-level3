package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TreeItem;

public class BlockTreeItemTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BlockTreeItemImpl((Block) null));
        assertThrows(NullPointerException.class, () -> new BlockTreeItemImpl((TreeItem<Block>) null));
    }

    @Test
    public void is_root_returnsTrue() {
        BlockTreeItem rootBlock = BlockTreeItemImpl.createRootBlockTreeItem();
        assertTrue(rootBlock.isRootBlock());
    }

}
