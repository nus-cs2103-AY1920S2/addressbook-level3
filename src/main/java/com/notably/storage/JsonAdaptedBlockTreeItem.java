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
 * Jackson-friendly version of {@link Object}.
 */
class JsonAdaptedBlockTreeItem {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String title;
    private final String body;
    private final List<JsonAdaptedBlockTreeItem> children = new ArrayList<JsonAdaptedBlockTreeItem>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
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
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedBlockTreeItem(BlockTreeItem source) {
        title = source.getTitle().getText();
        body = source.getBody().getText();
        children.addAll(source.getBlockChildren()
            .stream().map(JsonAdaptedBlockTreeItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
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
        if (!Body.isValidBody(body)) {
            throw new IllegalValueException(Body.MESSAGE_CONSTRAINTS);
        }
        final Body modelBody = new Body(body);

        final Block modelBlock = new BlockImpl(modelTitle, modelBody);

        final List<BlockTreeItem> children = new ArrayList<BlockTreeItem>();
        for (JsonAdaptedBlockTreeItem jsonAdaptedBlockTreeItem : this.children) {
            BlockTreeItem blockTreeItem = jsonAdaptedBlockTreeItem.toModelType();
            children.add(blockTreeItem);
        }

        final BlockTreeItem modelBlockTreeItem = new BlockTreeItemImpl(modelBlock);
        modelBlockTreeItem.setBlockChildren(children);

        return modelBlockTreeItem;
    }

}
