package com.notably.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.Title;
import com.notably.model.block.Body;
import com.notably.model.block.exceptions.CannotModifyRootException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;

public class BlockModelTest {
    private BlockModel blockModel;

    @BeforeEach
    public void setUpBeforeEach() {
        blockModel = new BlockModelImpl();
    }

    @Test
    public void constructor() {
        assertEquals(blockModel.getBlockTree()
            .getRootBlock()
            .getTreeItem()
            .getChildren(), FXCollections.emptyObservableList());
        assertEquals(blockModel.getCurrentlyOpenPath(), AbsolutePath.TO_ROOT_PATH);
    }

    @Test
    public void addBlockToCurrentPath_addToRoot() throws InvalidPathException {
        Block newBlock = new BlockImpl(new Title("CS2103"));
        blockModel.addBlockToCurrentPath(newBlock);
        assertTrue(blockModel.hasPath(AbsolutePath.fromString("/CS2103")));
    }

    @Test
    public void removeBlock_removeRoot() {
        assertThrows(CannotModifyRootException.class, () -> blockModel.removeBlock(AbsolutePath.TO_ROOT_PATH));
    }

    @Test
    public void updateCurrentlyOpenBlockBody_setRootBody_throwsCannotModifyRootException () {
        assertThrows(CannotModifyRootException.class,
            () -> blockModel.updateCurrentlyOpenBlockBody(new Body("New Body")));
    }

    @Nested
    public class BlockModelTest_withSampleData {
        @BeforeEach
        public void setUpBeforeEach() {
            blockModel = new BlockModelImpl();
            blockModel.addBlockToCurrentPath(new BlockImpl(new Title("CS2103")));
            blockModel.addBlockToCurrentPath(new BlockImpl(new Title("CS3230")));
        }

        @Test
        public void setCurrentlyOpenBlock_changePath() throws InvalidPathException {
            blockModel.setCurrentlyOpenBlock(AbsolutePath.fromString("/CS2103"));
            assertEquals(blockModel.getCurrentlyOpenPath(), AbsolutePath.fromString("/CS2103"));
        }

        @Test
        public void addBlockToCurrentPath_addToNonRootPath() throws InvalidPathException {
            blockModel.setCurrentlyOpenBlock(AbsolutePath.fromString("/CS3230"));
            blockModel.addBlockToCurrentPath(new BlockImpl(new Title("Week1")));
            blockModel.addBlockToCurrentPath(new BlockImpl(new Title("Week2")));
            blockModel.setCurrentlyOpenBlock(AbsolutePath.TO_ROOT_PATH);
            assertTrue(blockModel.hasPath(AbsolutePath.fromString("/CS3230/Week1")));
            assertTrue(blockModel.hasPath(AbsolutePath.fromString("/CS3230/Week2")));
        }

        @Test
        public void removeBlock_nonRootPath() throws InvalidPathException, CannotModifyRootException {
            blockModel.setCurrentlyOpenBlock(AbsolutePath.fromString("/CS3230"));
            blockModel.addBlockToCurrentPath(new BlockImpl(new Title("Week1")));
            blockModel.setCurrentlyOpenBlock(AbsolutePath.TO_ROOT_PATH);
            blockModel.removeBlock(AbsolutePath.fromString("/CS3230/Week1"));
        }

        @Test
        public void updateCurrentlyOpenBlockBody_nonRootPath() throws InvalidPathException, CannotModifyRootException {
            blockModel.setCurrentlyOpenBlock(AbsolutePath.fromString("/CS3230"));
            assertEquals(blockModel.getBlockTree()
                .get(blockModel.getCurrentlyOpenPath())
                .getBody()
                .getText(), "");
            blockModel.updateCurrentlyOpenBlockBody(new Body("These are notes."));
            assertEquals(blockModel.getBlockTree()
                .get(blockModel.getCurrentlyOpenPath())
                .getBody()
                .getText(), "These are notes.");
        }
    }
}