package com.notably.model.block;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import com.notably.model.block.exceptions.DuplicateBlockException;
import com.notably.model.block.exceptions.NoSuchBlockException;

import javafx.scene.control.TreeItem;

/**
 * Implementation class of BlockTreeItem.
 */
public class BlockTreeItemImpl implements BlockTreeItem {
    private TreeItem<Block> blockTreeItem;

    public BlockTreeItemImpl(Block block) {
        requireNonNull(block);
        blockTreeItem = new TreeItem<Block>(block);
    }

    public BlockTreeItemImpl(TreeItem<Block> treeItem) {
        requireNonNull(treeItem);
        blockTreeItem = treeItem;
    }

    public static BlockTreeItem createRootBlockTreeItem() {
        return new BlockTreeItemImpl(BlockImpl.createRootBlock());
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
        return new BlockTreeItemImpl(blockTreeItem.getParent());
    }

    @Override
    public List<BlockTreeItem> getBlockChildren() {
        return blockTreeItem.getChildren().stream()
            .map(child -> new BlockTreeItemImpl(child)).collect(Collectors.toList());
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
        BlockTreeItem child = getBlockChildren()
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
        boolean hasMatchingChild = getBlockChildren()
            .stream()
            .anyMatch(child -> child
                .getTitle()
                .equals(block.getTitle()));
        if (hasMatchingChild) {
            throw new DuplicateBlockException();
        }
        blockTreeItem.getChildren().add(new TreeItem<Block>(block));
    }

    @Override
    public void removeBlockChild(Block toRemove) {
        requireNonNull(toRemove);
        TreeItem<Block> itemToRemove = getBlockChild(toRemove.getTitle()).getTreeItem();
        blockTreeItem.getChildren().remove(itemToRemove);
    }

    @Override
    public boolean isRootBlock() {
        return getTitle()
            .getText()
            .equals("Root")
            && getTreeItem().getParent() == null;
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
        return otherBlock.getTitle().equals(this.getTitle());
    }

    @Override
    public int hashCode() {
        return hash(this.getTitle(), this.getTreeItem().getParent());
    }

}
