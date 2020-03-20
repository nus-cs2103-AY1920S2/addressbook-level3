package com.notably.model.block;

import java.util.List;

import javafx.scene.control.TreeItem;

/**
 * API of the BlockNode component.
 */
public interface BlockTreeItem extends Block {
    /**
     * Gets the {@code TreeItem<Block>} representation of the BlockTreeItem.
     */
    TreeItem<Block> getTreeItem();

    /**
     * Gets the Block content contained in a BlockTreeItem.
     */
    Block getBlock();

    /**
     * Gets the parent block of a block, if possible.
     * Returns an {@code Optional.empty()} if the block is the root block.
     */
    BlockTreeItem getBlockParent();

    /**
     * Gets a list of children blocks of a block.
     */
    List<BlockTreeItem> getBlockChildren();

    /**
     * Replaces all the children of the block with a new list of children.
     */
    void setBlockChildren(List<BlockTreeItem> newChildren);

    /**
     * Finds a child block of a block, with the given input.
     */
    BlockTreeItem getBlockChild(Title title);

    /**
     * Replaces a child block of a block, that matches the title, with a new child block.
     */
    void setBlockChild(Title title, Block newBlock);

    /**
     * Adds a single new child to a block.
     */
    void addBlockChild(Block newBlock);

    /**
     * Removes a specified child block from a block.
     * @param toRemove
     */
    void removeBlockChild(Block toRemove);

    /**
     * Checks if a block is a root block.
     */
    boolean isRootBlock();
}
