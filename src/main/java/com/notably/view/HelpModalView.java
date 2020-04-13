package com.notably.view;

import static com.notably.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import com.notably.commons.Help;
import com.notably.commons.LogsCenter;

import com.notably.commons.compiler.Compiler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class HelpModalView extends ViewPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-w17-2.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpModalView.class);
    private static final String FXML = "HelpWindow.fxml";

    private static Stage stage;

    @FXML
    private WebView commandSummary;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new help modal.
     *
     * @param root Stage to use as the root of the help modal.
     */
    public HelpModalView(Stage root) {
        super(FXML, root);

        requireAllNonNull(root);
        this.stage = root;

        helpMessage.setText(HELP_MESSAGE);
        setCommandSummary();
    }

    /**
     * Creates a new help modal.
     */
    public HelpModalView() {
        this(new Stage());
    }

    /**
     * Loads the Command Summary text into the CommandSummary webview component.
     */
    private void setCommandSummary() {
        String markdownContent = Help.getCommandSummaryMarkdown();
        String htmlContent = Compiler.compile(markdownContent);
        commandSummary.getEngine().loadContent(htmlContent);
        commandSummary.getEngine().setUserStyleSheetLocation(getClass()
                .getResource("/view/blockcontent/BlockContentDisplay.css").toExternalForm());
    }

    /**
     * Shows the help modal.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.info("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help modal is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help modal.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help modal.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
        logger.info("Copied User Guide Url");
    }
}
