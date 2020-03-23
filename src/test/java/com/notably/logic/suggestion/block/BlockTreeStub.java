package com.notably.logic.suggestion.block;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeItem;

/**
 * A Stub class for BlockTree.
 */
public class BlockTreeStub implements BlockTree {
    private BlockTreeItem root;

    @Override
    public BlockTreeItem getRootBlock() {
        return this.root;
    }

    @Override
    public BlockTreeItem get(AbsolutePath path) {
        return null;
    }

    @Override
    public void add(AbsolutePath path, Block newBlock) {

    }

    @Override
    public void set(AbsolutePath path, Block newBlock) {

    }

    @Override
    public void remove(AbsolutePath path) {

    }
}
