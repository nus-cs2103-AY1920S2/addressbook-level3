package seedu.zerotoone.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Controller for a help page
 */
public class ReportWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ReportWindow.class);
    private static final String FXML = "ReportWindow.fxml";

    @FXML
    private Label message;

    /**
     * Creates a new ReportWindow.
     *
     * @param root Stage to use as the root of the ReportWindow.
     */
    public ReportWindow(Stage root) {
        super(FXML, root);
        message.setText("DISPLAY SOME MEANINGFUL STATS HERE");
    }

    /**
     * Creates a new HelpWindow.
     */
    public ReportWindow() {
        this(new Stage());
    }

    /**
     * Shows the report window.
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
     * Returns true if the report window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the report window.
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
}
