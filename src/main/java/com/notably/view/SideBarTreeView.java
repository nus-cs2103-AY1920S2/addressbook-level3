package com.notably.view;

import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;

import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;

/**
 *
 */
public class SideBarTreeView extends ViewPart<Region> {

    private static final String FXML = "SideBarTreeView.fxml";

    @FXML
    private TreeView<Block> sideBarTreeView;

    public SideBarTreeView(BlockTree blockTree) {
        super(FXML);
        initializeTree(blockTree);
    }

    /**
     * Calls helper functions that set the tree settings and populate the SideBarTreeView with data.
     * @param blockTree The custom tree used to populate the TreeView.
     */
    private void initializeTree(BlockTree blockTree) {
        loadTreeItems(blockTree);
        initializeTreeSettings();
    }

    private void loadTreeItems(BlockTree blockTree) {
        sideBarTreeView.setRoot(blockTree.getRootBlock().getTreeItem());
        sideBarTreeView.setCellFactory(treeView -> new SideBarTreeViewCell());
    }

    /**
     * Provides an alternative way to specify TreeView settings, versus via fxml tags.
     */
    private void initializeTreeSettings() {
        sideBarTreeView.setEditable(false);
        sideBarTreeView.getRoot().setExpanded(true);
    }

    /**
     * Custom {@code TreeCell} that displays the text of a {@code Block}.
     */
    class SideBarTreeViewCell extends TreeCell<Block> {
        @Override
        protected void updateItem(Block block, boolean empty) {
            super.updateItem(block, empty);

            if (empty || block == null) {
                setText(null);
            } else {
                setText(getNoteTitle());
            }
        }

        private String getNoteTitle() {
            return getItem().getTitle().getText();
        }
    }
}
