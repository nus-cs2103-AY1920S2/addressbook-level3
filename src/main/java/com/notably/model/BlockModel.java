package com.notably.model;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeItem;

/**
 * API of the BlockModel component.
 */
public interface BlockModel {
    /**
     * Gets the BlockTree currently in use by Notably.
     */
    BlockTree getBlockTree();

    /**
     * Gets the BlockTreeItem that is currently open.
     */
    BlockTreeItem getCurrentlyOpenBlock();

    /**
     * Check if currently open block has the specified child block.
     */
    boolean hasPath(AbsolutePath p);

    /**
     * Adds a block to the specified path, if possible.
     */
    void addBlock(AbsolutePath p, Block b);

    /**
     * Removes the block at the specified path, if possible.
     */
    void removeBlock(AbsolutePath p);

    /**
     * Opens the block at the specified path, if possible.
     */
    void setCurrentlyOpenBlock(AbsolutePath p);

    /**
     * Sets the currently open block to a new block. (Replaces its block content)
     */
    void updateCurrentlyOpenBlockBody(Block newBlock);

    /**
     * Gets the absolute path of the currently open block.
     */
    AbsolutePath getBlockPath();

    /*
    TODO: To implement after storage classes are ready
    void setBlockTree(List<Block> blocks);
    void resetData(BlockModel newData);
    */
}
