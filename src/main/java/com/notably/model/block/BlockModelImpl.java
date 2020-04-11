package com.notably.model.block;

import java.util.Objects;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.block.exceptions.NoSuchBlockException;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The implementation class of {@link BlockModel}.
 */
public class BlockModelImpl implements BlockModel {
    private BlockTree blockTree;
    private Property<AbsolutePath> currentlyOpenPath;

    /**
     * Creates a new {@link BlockModel} implementation object.
     */
    public BlockModelImpl() {
        blockTree = new BlockTreeImpl();
        currentlyOpenPath = new SimpleObjectProperty<AbsolutePath>(AbsolutePath.fromString("/"));
    }

    @Override
    public BlockTree getBlockTree() {
        return blockTree;
    }

    @Override
    public void setBlockTree(BlockTree blockTree) {
        this.blockTree = blockTree;
    }

    @Override
    public void resetData(BlockModel newData) {
        Objects.requireNonNull(newData);
        setBlockTree(newData.getBlockTree());
        setCurrentlyOpenBlock(newData.getCurrentlyOpenPath());
    }

    @Override
    public AbsolutePath getCurrentlyOpenPath() {
        return currentlyOpenPath.getValue();
    }

    @Override
    public Property<AbsolutePath> currentlyOpenPathProperty() {
        return currentlyOpenPath;
    }

    @Override
    public boolean hasPath(AbsolutePath path) {
        try {
            blockTree.get(path);
            return true;
        } catch (NoSuchBlockException e) {
            return false;
        }
    }

    @Override
    public void setCurrentlyOpenBlock(AbsolutePath path) {
        if (!hasPath(path)) {
            throw new NoSuchBlockException(path.getStringRepresentation());
        }
        currentlyOpenPath.setValue(path);
    }

    @Override
    public void addBlockToCurrentPath(Block block) {
        blockTree.add(getCurrentlyOpenPath(), block);
    }

    @Override
    public void removeBlock(AbsolutePath path) {
        BlockTreeItem parent = blockTree.get(path).getBlockParent();
        blockTree.remove(path);

        // If the path no longer exists, find the nearest predecessor i.e case of deleting some unrelated block
        if (!hasPath(getCurrentlyOpenPath())) {
            setCurrentlyOpenBlock(parent.getAbsolutePath());
        }
    }

    @Override
    public void updateCurrentlyOpenBlockBody(Body newBody) {
        Block newBlock = new BlockImpl(blockTree.get(getCurrentlyOpenPath()).getTitle(), newBody);
        blockTree.set(getCurrentlyOpenPath(), newBlock);
    }
}
