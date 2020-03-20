package com.notably.model.block;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.notably.model.block.exceptions.DuplicateBlockException;
import com.notably.model.block.exceptions.NoSuchBlockException;

import javafx.scene.control.TreeItem;

/**
 * Represents a BlockTreeItem in the Notably data structure.
 */
public class BlockTreeItemImpl implements BlockTreeItem {
    private TreeItem<Block> blockTreeItem;
    private BlockTreeItem parent;
    private List<BlockTreeItem> children;

    public BlockTreeItemImpl(Block block, BlockTreeItem parent) {
        requireNonNull(block);
        blockTreeItem = new TreeItem<Block>(block);
        this.parent = parent;
        this.children = new ArrayList<BlockTreeItem>();
    }

    public static BlockTreeItem createRootBlockTreeItem() {
        return new BlockTreeItemImpl(BlockImpl.createRootBlock(), null);
    }

    @Override
    public Title getTitle() {
        return blockTreeItem.getValue().getTitle();
    }

    @Override
    public Body getBody() {
        return blockTreeItem.getValue().getBody();
    }

    @Override
    public TreeItem<Block> getTreeItem() {
        return this.blockTreeItem;
    }

    @Override
    public Block getBlock() {
        return blockTreeItem.getValue();
    }

    @Override
    public BlockTreeItem getBlockParent() {
        return this.parent;
    }

    @Override
    public List<BlockTreeItem> getBlockChildren() {
        return this.children;
    }

    @Override
    public void setBlockChildren(List<BlockTreeItem> newChildren) {
        requireNonNull(newChildren);
        List<TreeItem<Block>> convertedNewChildren = newChildren.stream()
            .map(newChild -> newChild.getTreeItem())
            .collect(Collectors.toList());
        blockTreeItem.getChildren().setAll(convertedNewChildren);
    }

    @Override
    public BlockTreeItem getBlockChild(Title title) throws NoSuchBlockException {
        requireNonNull(title);
        BlockTreeItem child = children
            .stream()
            .filter(blockTreeItem -> blockTreeItem
                .getTitle()
                .equals(title))
            .findFirst()
            .orElseThrow(() -> new NoSuchBlockException(title.getText()));
        return child;
    }

    @Override
    public void setBlockChild(Title title, Block newBlock) {
        requireNonNull(title);
        requireNonNull(newBlock);
        BlockTreeItem child = getBlockChild(title);
        child.getTreeItem().setValue(newBlock);
    }

    @Override
    public void addBlockChild(Block block) throws DuplicateBlockException {
        requireNonNull(block);
        boolean hasMatchingChild = children
            .stream()
            .anyMatch(child -> child
                .getTitle()
                .equals(block.getTitle()));
        if (hasMatchingChild) {
            throw new DuplicateBlockException();
        } else {
            BlockTreeItem newBlock = new BlockTreeItemImpl(block, this);
            children.add(newBlock);
            setBlockChildren(children);
        }
    }

    @Override
    public void removeBlockChild(Block toRemove) {
        requireNonNull(toRemove);
        BlockTreeItem itemToRemove = getBlockChild(toRemove.getTitle());
        children.remove(itemToRemove);
        setBlockChildren(children);
    }

    @Override
    public boolean isRootBlock() {
        return getTitle()
            .getText()
            .equals("Root")
            && parent == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (blockTreeItem == obj) {
            return true;
        }

        if (!(obj instanceof BlockTreeItem)) {
            return false;
        }

        BlockTreeItem otherBlock = (BlockTreeItem) obj;
        return otherBlock.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return hash(this.getTitle(), this.getBlockParent());
    }

}
