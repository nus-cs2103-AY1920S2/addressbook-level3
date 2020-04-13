package com.notably.view.blockcontent;

import static java.util.Objects.requireNonNull;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.Logic;
import com.notably.logic.exceptions.EditBlockBodyException;
import com.notably.model.Model;
import com.notably.model.block.BlockTreeItem;
import com.notably.view.ViewOnlyModal;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * View-Controller for the Editable view of the currently open block's content.
 */
public class BlockContentEditView extends ViewOnlyModal {

    private static final String FXML = "blockcontent/BlockContentEditView.fxml";
    private static final String LOGGER_SOURCE = "Edit Modal";

    protected final Logic logic;

    @FXML
    private TextArea blockContentTextArea;

    public BlockContentEditView(Logic logic, Model model) {
        super(FXML, new Stage(), model, model.blockEditableProperty(), LOGGER_SOURCE);
        requireNonNull(logic);
        this.logic = logic;
    }

    @Override
    protected void setChangeListeners() {
        setContentChangeListeners();
        super.setChangeListeners();
    }

    @Override
    protected void setModelProperty(Boolean bool) {
        model.setBlockEditable(bool);
    }

    /**
     * Closes the Block Edit modal after saving the changes to the note's contents.
     */
    @Override
    @FXML
    public void handleClose() {
        saveData();
        super.handleClose();
    }

    /**
     * Sets listeners that update the text content in the Block Edit modal to that of the currently
     * opened note, and whenever there are changes to the {@link com.notably.model.block.BlockTree};
     */
    protected void setContentChangeListeners() {
        model.currentlyOpenPathProperty().addListener(observable -> setText(model));
        model.getBlockTree().getRootBlock().getTreeItem()
                .addEventHandler(TreeItem.treeNotificationEvent(), event -> setText(model));
    }

    /**
     * Sets up the view's initial data and wire up all required change listeners.
     */
    protected void setInitialData() {
        setText(model);
    }

    /**
     * Sets the {@link TextArea}'s content to the currently open block's content in markdown form.
     *
     * @param model App's model
     */
    protected void setText(Model model) {
        requireNonNull(model);

        AbsolutePath currentlyOpenPath = model.getCurrentlyOpenPath();
        BlockTreeItem currentlyOpenBlock = model.getBlockTree().get(currentlyOpenPath);

        String markdownBody = currentlyOpenBlock.getBody().getText();

        blockContentTextArea.setText(markdownBody);
    }

    /**
     * Saves the data in the Block Edit Modal by notifying the {@link Logic} layer of the app.
     */
    private void saveData() {
        try {
            logic.editCurrentBlockBody(blockContentTextArea.getText());
            logger.info("Writing new Note's contents to file...");
        } catch (EditBlockBodyException e) {
            logger.warning("Unable to write new contents to file.");
        }
    }
}
