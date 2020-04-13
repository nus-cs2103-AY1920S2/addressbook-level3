//@@author PotatoCombat

package tatracker.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandDictionary;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-w17-4.github.io/TA-Tracker/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final ObservableList<CommandDetails> COMMAND_DETAILS = FXCollections
            .observableArrayList(CommandDictionary.getDetails());

    @FXML
    private StackPane helpListPanelPlaceholder;

    @FXML
    private Button copyButton;

    @FXML
    private Label website;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root, GuiSettings guiSettings) {
        super(FXML, root);

        if (guiSettings.getWindowCoordinates() != null) {
            root.setX(guiSettings.getWindowCoordinates().getX());
            root.setY(guiSettings.getWindowCoordinates().getY());
        }
        website.setText(USERGUIDE_URL);

        HelpListPanel helpListPanel = new HelpListPanel(COMMAND_DETAILS);
        helpListPanelPlaceholder.getChildren().add(helpListPanel.getRoot());

        //@@author fatin99

        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                logger.info("escaped");

                if (t.getCode() == KeyCode.ESCAPE) {
                    getRoot().show();
                    logger.info("click on escape");
                    root.close();
                }
            }
        });
    }

    //@@author PotatoCombat

    /**
     * Creates a new HelpWindow.
     * @param guiSettings for resizing the window.
     */
    public HelpWindow(GuiSettings guiSettings) {
        this(new Stage(), guiSettings);
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
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
    }
}
