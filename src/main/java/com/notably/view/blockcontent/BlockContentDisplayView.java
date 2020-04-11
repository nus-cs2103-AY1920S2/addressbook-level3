package com.notably.view.blockcontent;

import java.util.Objects;

import com.notably.commons.compiler.Compiler;
import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.block.BlockTreeItem;
import com.notably.view.ViewPart;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.web.WebView;

/**
 * Read-only and rendered view of the currently open block's content.
 */
public class BlockContentDisplayView extends ViewPart<WebView> {

    private static final String FXML = "blockcontent/BlockContentDisplayView.fxml";
    private final Model model;

    @FXML
    private WebView blockContentDisplay;

    public BlockContentDisplayView(Model model) {
        super(FXML);
        Objects.requireNonNull(model);
        this.model = model;
        setup();
    }

    /**
     * Sets up the view's initial data and wires up all required change listeners.
     */
    private void setup() {
        setBlockContentDisplay(model);
        setChangeListeners();
    }

    /**
     * Updates the content of the view-components to that of the currently open block.
     *
     * @param model App's model.
     */
    private void setBlockContentDisplay(Model model) {
        Objects.requireNonNull(model);

        AbsolutePath currentlyOpenPath = model.getCurrentlyOpenPath();
        BlockTreeItem currentlyOpenBlock = model.getBlockTree().get(currentlyOpenPath);

        String htmlTitle = getHtmlTitle(currentlyOpenBlock);
        String htmlBody = getHtmlBody(currentlyOpenBlock);
        String htmlContent = htmlTitle + htmlBody;

        blockContentDisplay.getEngine().loadContent(htmlContent);
    }

    /**
     * Helper function to retrieve the currently open block's {@link com.notably.model.block.Title}
     * rendered as HTML.
     *
     * @param currentlyOpenBlock A {@link BlockTreeItem} representing the currently open note block.
     * @return A String representing the note's body.
     */
    private String getHtmlTitle(BlockTreeItem currentlyOpenBlock) {
        Objects.requireNonNull(currentlyOpenBlock);

        String markdownTitle = "# " + currentlyOpenBlock.getTitle().getText();
        String htmlTitle = Compiler.compile(markdownTitle);
        return htmlTitle;
    }

    /**
     * Helper function to retrieve the currently open block's {@link com.notably.model.block.Body}
     * rendered as HTML.
     *
     * @param currentlyOpenBlock A {@link BlockTreeItem} representing the currently open note block.
     * @return A String representing the note's body.
     */
    private String getHtmlBody(BlockTreeItem currentlyOpenBlock) {
        Objects.requireNonNull(currentlyOpenBlock);

        String markdownBody = currentlyOpenBlock.getBody().getText();
        String htmlBody = Compiler.compile(markdownBody);
        return htmlBody;
    }

    /**
     * Sets up listeners for View state management.
     */
    private void setChangeListeners() {
        model.currentlyOpenPathProperty().addListener(observable -> setBlockContentDisplay(model));
        model.getBlockTree().getRootBlock().getTreeItem()
                .addEventHandler(TreeItem.treeNotificationEvent(), event -> setBlockContentDisplay(model));
        blockContentDisplay.getEngine().setUserStyleSheetLocation(getClass()
                .getResource("/view/blockcontent/BlockContentDisplay.css").toExternalForm());
        blockContentDisplay.getEngine().loadContent(htmlBody);
    }
}

