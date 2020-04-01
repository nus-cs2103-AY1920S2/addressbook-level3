package com.notably.view.blockcontent;

import java.util.Objects;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.block.BlockTreeItem;
import com.notably.view.ViewPart;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;

/**
 * Editable view of the currently open block's content.
 */
public class BlockContentEditView extends ViewPart<Region> {
    private static final String FXML = "blockcontent/BlockContentEditView.fxml";

    private final Model model;

    @FXML
    private TextArea blockContentTextArea;

    public BlockContentEditView(Model model) {
        super(FXML);

        Objects.requireNonNull(model);
        this.model = model;

        setup();
    }

    /**
     * Sets up the view's initial data and wire up all required change listeners.
     */
    private void setup() {
        // Initialize text area content
        setText(model);

        // Setup change listeners
        model.currentlyOpenPathProperty().addListener(observable -> setText(model));
        model.getBlockTree().getRootBlock().getTreeItem()
                .addEventHandler(TreeItem.treeNotificationEvent(), event -> setText(model));
    }

    /**
     * Sets the {@link TextArea}'s content of with the currently open block's Markdown body.
     *
     * @param model App's model
     */
    private void setText(Model model) {
        Objects.requireNonNull(model);

        AbsolutePath currentlyOpenPath = model.getCurrentlyOpenPath();
        BlockTreeItem currentlyOpenBlock = model.getBlockTree().get(currentlyOpenPath);
        String markdownBody = currentlyOpenBlock.getBody().getText();

        blockContentTextArea.setText(markdownBody);
    }
}

