package com.notably.model.block;

import java.util.List;
import java.util.Optional;

/**
 * API of the BlockModel component.
 */
public interface BlockModel {
    /**
     * Gets the title of a block.
     */
    Title getTitle();

    /**
     * Gets the body content of a block.
     */
    Body getBody();

    /**
     * Gets the parent block of a block, if possible.
     * Returns an {@code Optional.empty()} if the block is the root block.
     */
    Optional<BlockModel> getParent();

    /**
     * Gets a list of children blocks of a block.
     */
    List<BlockModel> getChildren();

    /**
     * Replaces all the children of the block with a new list of children
     */
    void setChildren(List<BlockModel> newChildren);

    /**
     * Finds a child block of a block, with the given input.
     */
    Optional<BlockModel> getChild(Title title);

    /**
     * Replaces a child block of a block, that matches the title, with a new child block.
     */
    void setChild(Title title, BlockModel newBlock);

    /**
     * Adds a single new child to a block.
     */
    void addChild(BlockModel newBlock);

    /**
     * Removes a specified child block from a block.
     * @param toRemove
     */
    void removeChild(BlockModel toRemove);

    /**
     * Checks if a block is a root block.
     */
    boolean isRoot();
}
