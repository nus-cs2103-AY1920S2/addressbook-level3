package com.notably.view.blockcontent;

import static com.notably.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.Logic;
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

    private final Logic logic;
    private final Model model;

    @FXML
    private TextArea blockContentTextArea;

    public BlockContentEditView(Logic logic, Model model) {
        super(FXML);

        requireAllNonNull(model, logic);
        this.logic = logic;
        this.model = model;

        initializeComponent();
    }

    /**
     * Sets up the view's initial data and wire up all required change listeners.
     */
    private void initializeComponent() {
        setText(model);
        initializeListeners();
    }

    /**
     * Sets the {@link TextArea}'s content to the currently open block's Markdown body.
     *
     * @param model App's model
     */
    private void setText(Model model) {
        requireNonNull(model);

        AbsolutePath currentlyOpenPath = model.getCurrentlyOpenPath();
        BlockTreeItem currentlyOpenBlock = model.getBlockTree().get(currentlyOpenPath);
        String markdownBody = currentlyOpenBlock.getBody().getText();

        blockContentTextArea.setText(markdownBody);
    }

    private void initializeListeners() {
        model.currentlyOpenPathProperty().addListener(observable -> setText(model));
        model.getBlockTree().getRootBlock().getTreeItem()
                .addEventHandler(TreeItem.treeNotificationEvent(), event -> setText(model));
    }
}

