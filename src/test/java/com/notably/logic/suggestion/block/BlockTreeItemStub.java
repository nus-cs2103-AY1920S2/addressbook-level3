package com.notably.logic.suggestion.block;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTreeItem;
import com.notably.model.block.Body;
import com.notably.model.block.Title;

import javafx.scene.control.TreeItem;

/**
 * A Stub class for BlockTreeItem.
 */
public class BlockTreeItemStub implements BlockTreeItem {
    private TreeItem<Block> blockTreeItem;
    private BlockTreeItem parent;
    private List<BlockTreeItem> children;

    public BlockTreeItemStub(Block block, BlockTreeItem parent) {
        requireNonNull(block);
        blockTreeItem = new TreeItem<Block>(block);
        this.parent = parent;
        this.children = new ArrayList<BlockTreeItem>();
    }

    public static BlockTreeItem createRootBlockTreeItem() {
        return new BlockTreeItemStub(new BlockImpl(new Title("Root")), null);
    }

    @Override
    public TreeItem<Block> getTreeItem() {
        return null;
    }

    @Override
    public Block getBlock() {
        return null;
    }

    @Override
    public BlockTreeItem getBlockParent() {
        return null;
    }

    @Override
    public List<BlockTreeItem> getBlockChildren() {
        return this.children;
    }

    @Override
    public void setBlockChildren(List<BlockTreeItem> newChildren) {

    }

    @Override
    public BlockTreeItem getBlockChild(Title title) {
        return null;
    }

    @Override
    public void setBlockChild(Title title, Block newBlock) {

    }

    @Override
    public void addBlockChild(Block newBlock) {

    }

    @Override
    public void removeBlockChild(Block toRemove) {

    }

    @Override
    public boolean isRootBlock() {
        return getTitle()
                .getText()
                .equals("Root")
                && parent == null;
    }

    @Override
    public Title getTitle() {
        return blockTreeItem.getValue().getTitle();
    }

    @Override
    public Body getBody() {
        return null;
    }
}
