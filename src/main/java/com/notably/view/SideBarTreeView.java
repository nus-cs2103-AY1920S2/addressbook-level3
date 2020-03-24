package com.notably.view;

import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeItem;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;

/**
 *
 */
public class SideBarTreeView extends ViewPart<Region> {

    private static final String FXML = "SideBarTreeView.fxml";

    @FXML
    private TreeView<String> sideBarTreeViewRoot;

    public SideBarTreeView(BlockTree blockTree) {
        super(FXML);
        loadTreeItems(blockTree);
    }

    private void loadTreeItems(BlockTree blockTree) {
        loadTreeItemsStub();
    }

    private void loadTreeItemsStub() {
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        TreeItem<String> notably = new TreeItem<>("Notably");
        notably.setExpanded(true);
        root.getChildren().add(notably);
        notably.getChildren().add(new TreeItem<>("Another Note"));
        notably.getChildren().add(new TreeItem<>("CS2103"));
        notably.getChildren().add(new TreeItem<>("ST2334"));

        sideBarTreeViewRoot.setRoot(root);
    }
}
