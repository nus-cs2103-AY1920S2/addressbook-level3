package com.notably.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.notably.commons.exceptions.IllegalValueException;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.BlockTreeItem;

/**
 * A BlockTree that is serializable to JSON format.
 */
@JsonRootName(value = "blockTree")
class JsonSerializableBlockTree {

    public static final String MESSAGE_DUPLICATE_BLOCK_CHILD = "Block's children list contains duplicate children.";

    private final List<JsonAdaptedBlockTreeItem> rootChildren = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBlockTree} with the given root blocks.
     */
    @JsonCreator
    public JsonSerializableBlockTree(@JsonProperty("root") List<JsonAdaptedBlockTreeItem> items) {
        this.rootChildren.addAll(items);
    }

    /**
     * Converts a given {@code BlockTree} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBlockTree}.
     */
    public JsonSerializableBlockTree(BlockTree source) {
        rootChildren.addAll(source.getRootBlock()
            .getBlockChildren()
            .stream()
            .map(JsonAdaptedBlockTreeItem::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this block tree into the model's {@code BlockTree} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BlockTree toModelType() throws IllegalValueException {
        BlockTree blockTree = new BlockTreeImpl();
        List<BlockTreeItem> rootChildren = new ArrayList<>();
        for (JsonAdaptedBlockTreeItem jsonAdaptedBlockTreeItem : this.rootChildren) {
            BlockTreeItem blockTreeItem = jsonAdaptedBlockTreeItem.toModelType();
            if (rootChildren.contains(blockTreeItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BLOCK_CHILD);
            }
            rootChildren.add(blockTreeItem);
        }
        if (!rootChildren.isEmpty()) {
            blockTree.getRootBlock().setBlockChildren(rootChildren);
        }
        return blockTree;
    }

}
