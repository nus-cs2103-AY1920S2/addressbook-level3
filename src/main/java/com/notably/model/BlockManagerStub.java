package com.notably.model;

import com.notably.commons.core.path.Path;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

/**
 * BlockManagerStub
 */
public class BlockManagerStub implements BlockManager {
    public boolean hasBlock(Block b) {
        return false;
    }

    public void addBlock(Block b) {

    }

    public void deleteBlock(Path p){

    }

    public Block openBlock(Path p) {
        return new BlockImpl(new Title("Path"), new Body("path"));
    }
}
