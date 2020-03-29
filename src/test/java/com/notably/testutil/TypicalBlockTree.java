package com.notably.testutil;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

public class TypicalBlockTree {
    public static final Block Y2S2 = new BlockImpl(new Title("Y2S2"));
    public static final Block CS2103T = new BlockImpl(new Title("CS2103T"), new Body("Software Engineering"));
    public static final Block CS2103T_LECTURES = new BlockImpl(new Title("Lecture Notes"));
    public static final Block CS2103T_TUTORIALS = new BlockImpl(new Title("Tutorials"));
    public static final Block CS2103T_TUTORIAL_1 = new BlockImpl(new Title("Tutorial 1"), new Body("Q1: True, Q2: False"));
    public static final Block CS2103T_TUTORIAL_2 = new BlockImpl(new Title("Tutorial 2"), new Body("Q1: False, Q2: False"));
    public static final Block CS2106 = new BlockImpl(new Title("CS2106"), new Body("Introduction to Operating Systems"));
    public static final Block CS2106_LECTURES = new BlockImpl(new Title("Lecture Notes"));
    public static final Block CS2106_TUTORIALS = new BlockImpl(new Title("Tutorials"));
    public static final Block CS2106_TUTORIAL_1 = new BlockImpl(new Title("Tutorial 1"), new Body("Q1: True, Q2: False"));

    public static BlockTree getTypicalBlockTree() {
        BlockTree blockTree = new BlockTreeImpl();
        blockTree.add(AbsolutePath.TO_ROOT_PATH, Y2S2);
        blockTree.add(AbsolutePath.fromString("/Y2S2"), CS2103T);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2103T"), CS2103T_LECTURES);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2103T"), CS2103T_TUTORIALS);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2103T/Tutorials"), CS2103T_TUTORIAL_1);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2103T/Tutorials"), CS2103T_TUTORIAL_2);
        blockTree.add(AbsolutePath.fromString("/Y2S2"), CS2106);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2106"), CS2106_LECTURES);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2106"), CS2106_TUTORIALS);
        blockTree.add(AbsolutePath.fromString("/Y2S2/CS2106/Tutorials"), CS2106_TUTORIAL_1);

        return blockTree;
    }
}