package com.notably.model.block;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.block.exceptions.CannotModifyRootException;
import com.notably.model.block.exceptions.NoSuchBlockException;

/**
 * Custom tree-like data structure that uses the AbsolutePath object
 * to obtain the BlockTreeItem needed for manipulation.
 */
public interface BlockTree {
    /**
     * Gets the root BlockTreeItem of the block tree.
     * @return The root BlockTreeItem.
     */
    BlockTreeItem getRootBlock();

    /**
     * Gets the BlockTreeItem at the path specified.
     * @param path Full path of the block.
     * @return
     */
    BlockTreeItem get(AbsolutePath path) throws NoSuchBlockException;

    /**
     * Adds a new Block to the block at the path specified.
     * @param path Full path of the parent block.
     * @param newBlock The block to be added.
     */
    void add(AbsolutePath path, Block newBlock);

    /**
     * Sets the block at the specified path to a new block.
     * @param path Full path of the block.
     * @param newBlock The new block to be set.
     */
    void set(AbsolutePath path, Block newBlock) throws CannotModifyRootException;

    /**
     * Remove the block at the specified path.
     * @param path Full path of the block to be removed.
     */
    void remove(AbsolutePath path) throws CannotModifyRootException;

}
