package com.notably.view;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.block.Block;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeItem;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;

/**
 * Represents the View-Controller for the SideBarTreeView component.
 */
public class SideBarTreeView extends ViewPart<Region> {

    private static final String FXML = "SideBarTreeView.fxml";
    private BlockTreeItem currentlyOpenedNote;

    @FXML
    private TreeView<Block> sideBarTreeView;

    public SideBarTreeView(BlockTree blockTree, Property<AbsolutePath> currentlyOpenPathProperty) {
        super(FXML);
        this.currentlyOpenedNote = blockTree.get(currentlyOpenPathProperty.getValue());
        autoUpdateTree(blockTree, currentlyOpenPathProperty);
        initializeTree();
    }

    /**
     * Listens to changes in the structure of the tree in the Model, and accordingly updates the
     * currently opened Note in the SidebarTreeView.
     * @param blockTree A custom object that represents the tree-like hierarchy of Notes.
     * @param currentlyOpenPathProperty The path to the currently opened Note.
     */
    private void autoUpdateTree(BlockTree blockTree, Property<AbsolutePath> currentlyOpenPathProperty) {
        currentlyOpenPathProperty.addListener((observable, oldValue, newValue) -> {
            this.currentlyOpenedNote = blockTree.get(AbsolutePath.fromString(newValue.toString()));
            initializeTree();
        });
    }

    /**
     * Calls helper functions that set the tree settings and populate the SideBarTreeView with data.
     */
    private void initializeTree() {
        setTreeSettings();
        setTreeStructure();
    }

    /**
     * Provides an alternative way to specify TreeView settings, versus via fxml tags.
     */
    private void setTreeSettings() {
        sideBarTreeView.setEditable(false);
    }

    /**
     * Calls helper functions that set the strategies for populating the SideBarTreeView with data.
     */
    private void setTreeStructure() {
        sideBarTreeView.setCellFactory(treeView -> new SideBarTreeViewCell());
        useLevelDisplayStrategy();
        useExpansionStrategy();
    }

    /**
     * Configures the SideBarTreeView to only display a 3-level nested hierarchy of Notes,
     * ie (Parent -> currently opened Note and its siblings -> direct children of currently opened Note).
     *
     * If the currently open Note is the root, the hierarchy is modified to look like:
     * (Root -> Root's children -> Root's grandchildren)
     */
    private void useLevelDisplayStrategy() {
        TreeItem<Block> treeParent = this.currentlyOpenedNote.getTreeItem().getParent();
        if (treeParent != null) {
            sideBarTreeView.setRoot(treeParent);
        } else {
            sideBarTreeView.setRoot(this.currentlyOpenedNote.getTreeItem());
        }
    }

    /**
     * Expands the first and second level nodes, and contracts the third level nodes as per the
     * 3-level Display Strategy of {@link #useLevelDisplayStrategy()}.
     */
    private void useExpansionStrategy() {
        sideBarTreeView.getRoot().setExpanded(true);
        sideBarTreeView.getRoot().getChildren()
                .forEach(child -> {
                    child.setExpanded(true);
                    child.getChildren().forEach(grandChild -> {
                        grandChild.setExpanded(false);
                    });
                });
    }

    /**
     * Custom {@code TreeCell} that displays the text of a {@code Block}.
     */
    class SideBarTreeViewCell extends TreeCell<Block> {
        @Override
        protected void updateItem(Block block, boolean empty) {
            super.updateItem(block, empty);

            setGraphic(null);
            if (empty || block == null) {
                setText(null);
            } else {
                setText(getNoteTitle());
                setSelectedProperty(block);
            }
        }

        private String getNoteTitle() {
            return getItem().getTitle().getText();
        }

        private void setSelectedProperty(Block block) {
            if ((block == currentlyOpenedNote.getBlock())) {
                updateSelected(true);
            } else {
                updateSelected(false);
            }
        }
    }
}
