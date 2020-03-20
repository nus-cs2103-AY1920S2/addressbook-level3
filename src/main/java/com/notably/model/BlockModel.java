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
     * Check if currently open block has the specified child block.
     */
    boolean hasChild(Block b);

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
    BlockTreeItem openBlock(AbsolutePath p);

    /**
     * Sets the currently open block to a new block. (Replaces its block content)
     */
    void saveBlock(Block newBlock);

    /**
     * Gets the absolute path of the block.
     * TODO: Revise usage and interaction with other components
     */
    AbsolutePath getBlockPath(BlockTreeItem b);

    /*
    TODO: To implement after storage classes are ready
    void setBlockTree(List<Block> blocks);
    void resetData(BlockModel newData);
    */
}
