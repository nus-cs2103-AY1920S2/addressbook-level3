package com.notably.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.notably.commons.exceptions.IllegalValueException;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTreeItem;
import com.notably.model.block.BlockTreeItemImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

/**
 * Jackson-friendly version of {@link BlockTreeItem}.
 */
class JsonAdaptedBlockTreeItem {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Block's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_BLOCK_CHILD = "Block's children list contains duplicate children.";

    private final String title;
    private final String body;
    private final List<JsonAdaptedBlockTreeItem> children = new ArrayList<JsonAdaptedBlockTreeItem>();

    /**
     * Constructs a {@code JsonAdaptedBlockTreeItem} with the given {@code BlockTreeItem} details.
     */
    @JsonCreator
    public JsonAdaptedBlockTreeItem(@JsonProperty("title") String title, @JsonProperty("body") String body,
        @JsonProperty("children") List<JsonAdaptedBlockTreeItem> children) {
        this.title = title;
        this.body = body;
        if (children != null) {
            this.children.addAll(children);
        }
    }

    /**
     * Converts a given {@code BlockTreeItem} into this class for Jackson use.
     */
    public JsonAdaptedBlockTreeItem(BlockTreeItem source) {
        title = source.getTitle().getText();
        body = source.getBody().getText();
        children.addAll(source.getBlockChildren()
            .stream().map(JsonAdaptedBlockTreeItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted {@code BlockTreeItem} object into the model's
     *  {@code BlockTreeItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public BlockTreeItem toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (body == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Body.class.getSimpleName()));
        }

        final Body modelBody = new Body(body);

        final Block modelBlock = new BlockImpl(modelTitle, modelBody);

        final List<BlockTreeItem> modelChildren = new ArrayList<>();
        for (JsonAdaptedBlockTreeItem jsonAdaptedBlockTreeItem : children) {
            BlockTreeItem blockTreeItem = jsonAdaptedBlockTreeItem.toModelType();
            if (modelChildren.contains(blockTreeItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BLOCK_CHILD);
            }
            modelChildren.add(blockTreeItem);
        }

        final BlockTreeItem modelBlockTreeItem = new BlockTreeItemImpl(modelBlock);

        if (!modelChildren.isEmpty()) {
            modelBlockTreeItem.setBlockChildren(modelChildren);
        }

        return modelBlockTreeItem;
    }
}
