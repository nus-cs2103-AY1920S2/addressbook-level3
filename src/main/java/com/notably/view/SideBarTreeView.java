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
        initializeTreeSettings();
        loadTreeItems(blockTree);
    }

    /**
     * Provides an alternative way to specify TreeView settings, versus via fxml tags.
     */
    private void initializeTreeSettings() {
        sideBarTreeView.setEditable(false);
    }

    private void loadTreeItems(BlockTree blockTree) {
        sideBarTreeView.setRoot(blockTree.getRootBlock().getTreeItem());
        sideBarTreeView.setCellFactory(treeView -> new SideBarTreeViewCell());
    }

    /*
    private void loadTreeItemsStub() {
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        TreeItem<String> notably = new TreeItem<>("Notably");
        notably.setExpanded(true);
        root.getChildren().add(notably);
        notably.getChildren().add(new TreeItem<>("Another Note"));
        notably.getChildren().add(new TreeItem<>("CS2103"));
        notably.getChildren().add(new TreeItem<>("ST2334"));

        //sideBarTreeViewRoot.setRoot(root);
    }*/

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
