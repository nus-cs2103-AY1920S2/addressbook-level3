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
 * An Immutable BlockTree that is serializable to JSON format.
 */
@JsonRootName(value = "blockTree")
class JsonSerializableBlockTree {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedBlockTreeItem> blocks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBlockTree} with the given persons.
     */
    @JsonCreator
    public JsonSerializableBlockTree(@JsonProperty("Root") List<JsonAdaptedBlockTreeItem> items) {
        this.blocks.addAll(items);
    }

    /**
     * Converts a given {@code BlockTree} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBlockTree}.
     */
    public JsonSerializableBlockTree(BlockTree source) {
        blocks.addAll(source.getRootBlock()
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
        for (JsonAdaptedBlockTreeItem jsonAdaptedBlockTreeItem : blocks) {
            BlockTreeItem blockTreeItem = jsonAdaptedBlockTreeItem.toModelType();
            // if (addressBook.hasPerson(person)) {
            //     throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            // }
            rootChildren.add(blockTreeItem);
        }
        blockTree.getRootBlock().setBlockChildren(rootChildren);
        return blockTree;
    }

}
