package com.notably.model;

import com.notably.model.block.Path;
import com.notably.model.block.Block;

public interface BlockManager {
    boolean hasBlock(Block b);

    void addBlock(Block b);

    void deleteBlock(Path p);

    Block openBlock(Path p);
}
