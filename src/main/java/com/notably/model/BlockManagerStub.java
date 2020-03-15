package com.notably.model;

import com.notably.model.block.Path;
import com.notably.model.block.Block;
import com.notably.model.block.BlockStub;

public class BlockManagerStub implements BlockManager {
    public boolean hasBlock(Block b) {
        return false;
    }

    public void addBlock(Block b) {

    }

    public void deleteBlock(Path p){

    }

    public Block openBlock(Path p) {
        return new BlockStub("Path", "path");
    }
}
