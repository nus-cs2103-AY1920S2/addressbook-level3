package com.notably.model;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.BlockTreeItem;
import com.notably.model.block.Body;
import com.notably.model.block.exceptions.CannotModifyRootException;
import com.notably.model.block.exceptions.NoSuchBlockException;

import javafx.beans.property.Property;

public class BlockModelImpl implements BlockModel {
    BlockTree blockTree;
	Property<AbsolutePath> currentlyOpenPath;
	Property<BlockTreeItem> currentlyOpenBlock;

    public BlockModelImpl() {
        blockTree = new BlockTreeImpl();
    }

	@Override
	public BlockTree getBlockTree() {
		return blockTree;
	}

	@Override
	public AbsolutePath getCurrentlyOpenPath() {
		return currentlyOpenPath.getValue();
	}

	@Override
	public Property<AbsolutePath> getCurrentlyOpenPathProperty() {
		return currentlyOpenPath;
	}

	@Override
	public boolean hasPath(AbsolutePath p) {
		try {
			blockTree.get(p);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void setCurrentlyOpenBlock(AbsolutePath p) throws NoSuchBlockException {
		blockTree.get(p);
		currentlyOpenPath.setValue(p);
	}

	@Override
	public void addBlockToCurrentPath(Block b) {
		blockTree.add(getCurrentlyOpenPath(), b);
	}

	@Override
	public void removeBlock(AbsolutePath p) throws CannotModifyRootException {
		blockTree.remove(p);
	}

	@Override
	public void updateCurrentlyOpenBlockBody(Body newBody) throws CannotModifyRootException {
		Block newBlock = new BlockImpl(blockTree.get(getCurrentlyOpenPath()).getTitle(), newBody);
		blockTree.set(getCurrentlyOpenPath(), newBlock);
	}

}