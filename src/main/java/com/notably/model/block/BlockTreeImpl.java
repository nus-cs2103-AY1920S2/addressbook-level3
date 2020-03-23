package com.notably.model.block;

import static java.util.Objects.requireNonNull;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.block.exceptions.CannotModifyRootException;
import com.notably.model.block.exceptions.NoSuchBlockException;

/**
 * Custom tree-like data structure that uses the AbsolutePath object
 * to obtain the Block needed for manipulation.
 */
public class BlockTreeImpl implements BlockTree {
    private BlockTreeItem root;

    public BlockTreeImpl() {
        this.root = BlockTreeItemImpl.createRootBlockTreeItem();
    }

    @Override
    public BlockTreeItem getRootBlock() {
        return this.root;
    }

    @Override
    public BlockTreeItem get(AbsolutePath path) throws NoSuchBlockException {
        requireNonNull(path);
        BlockTreeItem currentBlock = root;
        for (String component : path.getComponents()) {
            currentBlock = currentBlock.getBlockChild(new Title(component));
        }
        return currentBlock;
    }

    @Override
    public void add(AbsolutePath path, Block newBlock) {
        requireNonNull(path);
        requireNonNull(newBlock);
        BlockTreeItem currentBlock = get(path);
        currentBlock.addBlockChild(newBlock);
    }

    @Override
    public void set(AbsolutePath path, Block newBlock) {
        requireNonNull(path);
        requireNonNull(newBlock);
        BlockTreeItem currentBlock = get(path);
        BlockTreeItem parentBlock;
        if (currentBlock.isRootBlock()) {
            throw new CannotModifyRootException();
        }
        parentBlock = currentBlock.getBlockParent();
        parentBlock.setBlockChild(currentBlock.getTitle(), newBlock);
    }

    @Override
    public void remove(AbsolutePath path) {
        requireNonNull(path);
        BlockTreeItem currentBlock = get(path);
        BlockTreeItem parentBlock;
        if (currentBlock.isRootBlock()) {
            throw new CannotModifyRootException();
        }
        parentBlock = currentBlock.getBlockParent();
        parentBlock.removeBlockChild(currentBlock);
    }
}
