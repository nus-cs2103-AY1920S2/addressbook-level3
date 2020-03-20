package com.notably.model.block;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.model.block.exceptions.CannotModifyRootException;
import com.notably.model.block.exceptions.DuplicateBlockException;
import com.notably.model.block.exceptions.NoSuchBlockException;

public class BlockTreeTest {
    private BlockTree blockTree = new BlockTreeImpl();
    private AbsolutePath toRoot;
    private AbsolutePath toCs2103;
    private AbsolutePath toCs2103Week1;
    private AbsolutePath toCs2103Week1Lecture;

    @BeforeEach
    public void setUp() throws InvalidPathException {
        toRoot = AbsolutePath.fromString("/");
        toCs2103 = AbsolutePath.fromString("/CS2103");
        toCs2103Week1 = AbsolutePath.fromString("/CS2103/Week1");
        toCs2103Week1Lecture = AbsolutePath.fromString("/CS2103/Week1/Lecture");
    }

    private void setUpTestTree() {
        Block newBlock1 = new BlockImpl(new Title("CS2103"));
        Block newBlock2 = new BlockImpl(new Title("CS3230"));
        Block newBlock3 = new BlockImpl(new Title("Week1"));
        blockTree.add(toRoot, newBlock1);
        blockTree.add(toRoot, newBlock2);
        blockTree.add(toCs2103, newBlock3);
    }

    @Test
    public void constructor() {
        assertTrue(blockTree.getRootBlock().isRootBlock());
        assertTrue(blockTree.getRootBlock().getBlockChildren().isEmpty());
    }

    @Test
    public void get_root() throws InvalidPathException {
        setUpTestTree();
        assertEquals(blockTree.get(toRoot), blockTree.getRootBlock());
    }

    @Test
    public void get_pathDoesNotExist_throwsNoSuchBlockException() throws InvalidPathException {
        setUpTestTree();
        AbsolutePath nonExistentPath = AbsolutePath.fromString("/SomeNonExistentPath");
        assertThrows(NoSuchBlockException.class, () -> blockTree.get(nonExistentPath));
    }

    @Test
    public void add_block() throws InvalidPathException {
        Block newBlock = new BlockImpl(new Title("Week2"));
        AbsolutePath toCs2103Week2 = AbsolutePath.fromString("/CS2103/Week2");

        setUpTestTree();
        blockTree.add(toCs2103, newBlock);
        assertEquals(blockTree.get(toCs2103Week2).getBlock(), newBlock);
    }

    @Test
    public void add_block_throwsDuplicateBlockException() {
        setUpTestTree();
        Block newBlock = new BlockImpl(new Title("CS2103"));
        assertThrows(DuplicateBlockException.class, () -> blockTree.add(toRoot, newBlock));
    }

    @Test
    public void add_block_deeperNestedBlock() {
        Block newBlock = new BlockImpl(new Title("Lecture"));

        setUpTestTree();
        blockTree.add(toCs2103Week1, newBlock);
        assertEquals(blockTree.get(toCs2103Week1Lecture).getBlock(), newBlock);
    }

    @Test
    public void remove_block() throws CannotModifyRootException {
        setUpTestTree();
        blockTree.remove(toCs2103Week1);
        assertThrows(NoSuchBlockException.class, () -> blockTree.get(toCs2103Week1));
    }

    @Test
    public void remove_root_throwsCannotModifyRootException() {
        setUpTestTree();
        assertThrows(CannotModifyRootException.class, () -> blockTree.remove(toRoot));
    }

    @Test
    public void set_root_throwsCannotModifyRootException() {
        Block newBlock = new BlockImpl(new Title("CS2107"));
        setUpTestTree();
        assertThrows(CannotModifyRootException.class, () -> blockTree.set(toRoot, newBlock));
    }
}
