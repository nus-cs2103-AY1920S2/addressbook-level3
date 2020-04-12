package com.notably.model.block;

import java.util.List;
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
        if (isCurrentlyOpenAffectedByDelete(path)) {
            BlockTreeItem parent = blockTree.get(path).getBlockParent();
            setCurrentlyOpenBlock(parent.getAbsolutePath());
        }
        blockTree.remove(path);
    }

    /**
     * Checks if the incoming delete command removes the currently open block as well,
     * i.e {@code currentlyOpenPath} is a descendent of {@code path}.
     *
     * @param path {@code AbsolutePath} of block to be deleted
     * @return Whether the currently open path is affected
     */
    private boolean isCurrentlyOpenAffectedByDelete(AbsolutePath path) {
        boolean match = true;
        List<String> currentOpenPathList = getCurrentlyOpenPath().getComponents();
        List<String> matchPathList = path.getComponents();
        if (matchPathList.size() > currentOpenPathList.size()) {
            return false;
        }

        // Handle deletion of root
        if (matchPathList.size() == 0) {
            return false;
        }

        for (int index = 0; index < matchPathList.size(); index++) {
            if (!Objects.equals(currentOpenPathList.get(index), matchPathList.get(index))) {
                match = false;
                break;
            }
        }
        return match;
    }

    @Override
    public void updateCurrentlyOpenBlockBody(Body newBody) {
        Block newBlock = new BlockImpl(blockTree.get(getCurrentlyOpenPath()).getTitle(), newBody);
        blockTree.set(getCurrentlyOpenPath(), newBlock);
    }
}
