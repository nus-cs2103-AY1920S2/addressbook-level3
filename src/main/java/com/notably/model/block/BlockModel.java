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
     * Returns an {@code Optional<Empty>} if the block is the root block.
     */
    Optional<Block> getParent();

    /**
     * Gets a list of children blocks of a block.
     */
    List<Block> getChildren();

    /**
     * Replaces all the children of the block with a new list of children
     */
    void setChildren(List<Block> newChildren);

    /**
     * Finds a child block of a block, with the given input.
     */
    Optional<Block> getChild(Title title);

    /**
     * Replaces a child block of a block, with a new child block.
     */
    void setChild(Block oldBlock, Block newBlock);

    /**
     * Adds a single new child to a block.
     */
    void addChild(Block newBlock);

    /**
     * Removes a specified child block from a block.
     * @param toRemove
     */
    void removeChild(Block toRemove);

    /**
     * Checks if a block is a root block.
     */
    boolean isRoot();
}
