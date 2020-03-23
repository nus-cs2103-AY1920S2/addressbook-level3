package com.notably.model.block;

import com.notably.commons.core.path.AbsolutePath;

import javafx.beans.property.Property;

/**
 * API of the BlockModel component.
 */
public interface BlockModel {
    /**
     * Gets the BlockTree currently in use by Notably.
     */
    BlockTree getBlockTree();

    /**
     * Gets the absolute path of the currently open block.
     */
    AbsolutePath getCurrentlyOpenPath();

    /**
     * Gets the {@code Property<AbsolutePath>} of the currently open path.
     */
    Property<AbsolutePath> getCurrentlyOpenPathProperty();

    /**
     * Check if currently open block has the specified child block.
     */
    boolean hasPath(AbsolutePath p);

    /**
     * Opens the block at the specified path, if possible. (For open command)
     */
    void setCurrentlyOpenBlock(AbsolutePath p);

    /**
     * Adds a block to the specified path, if possible. (For add command)
     */
    void addBlockToCurrentPath(Block b);

    /**
     * Removes the block at the specified path, if possible. (For remove command)
     */
    void removeBlock(AbsolutePath p);

    /**
     * Sets the currently open block to a new block. (For edit command)
     */
    void updateCurrentlyOpenBlockBody(Body newBody);

    /*
    TODO: To implement after storage classes are ready
    void setBlockTree(List<Block> blocks);
    void resetData(BlockModel newData);
    */
}
