package com.notably.storage;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.notably.commons.exceptions.IllegalValueException;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTreeItem;
import com.notably.model.block.BlockTreeItemImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

public class JsonAdaptedBlockTreeItemTest {
    private final BlockTreeItem item1 = new BlockTreeItemImpl(new BlockImpl(new Title("Item1")));

    @Test
    public void toModelType_validBlockTreeItem_returnsBlockTreeItem() throws Exception {
        JsonAdaptedBlockTreeItem blockTreeItem = new JsonAdaptedBlockTreeItem(item1);
        assertEquals(item1, blockTreeItem.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedBlockTreeItem blockTreeItem = new JsonAdaptedBlockTreeItem("!nv@lid", "Body",
            new ArrayList<JsonAdaptedBlockTreeItem>());
        assertThrows(IllegalValueException.class, Title.MESSAGE_CONSTRAINTS, blockTreeItem::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedBlockTreeItem blockTreeItem = new JsonAdaptedBlockTreeItem(null, "Body",
            new ArrayList<JsonAdaptedBlockTreeItem>());
        assertThrows(IllegalValueException.class, String.format(JsonAdaptedBlockTreeItem.MISSING_FIELD_MESSAGE_FORMAT,
            Title.class.getSimpleName()), blockTreeItem::toModelType);
    }

    @Test
    public void toModelType_nullBody_throwsIllegalValueException() {
        JsonAdaptedBlockTreeItem blockTreeItem = new JsonAdaptedBlockTreeItem("Title", null,
            new ArrayList<JsonAdaptedBlockTreeItem>());
        assertThrows(IllegalValueException.class, String.format(JsonAdaptedBlockTreeItem.MISSING_FIELD_MESSAGE_FORMAT,
            Body.class.getSimpleName()), blockTreeItem::toModelType);
    }
}
