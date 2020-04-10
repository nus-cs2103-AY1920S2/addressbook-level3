package team.easytravel.ui.packinglisttab;

import java.util.logging.Logger;

import javafx.stage.Stage;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.ui.UiPart;

/**
 * The type List preset window.
 */
public class ListPresetWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ListPresetWindow.class);
    private static final String FXML = "packinglisttab/ListPresetWindow.fxml";

    /**
     * Creates a new ListPresetWindow.
     *
     * @param root Stage to use as the root of the ListPresetWindow.
     */
    public ListPresetWindow(Stage root) {
        super(FXML, root);
        root.setTitle("List Preset");
    }

    /**
     * Creates a new ListWindow.
     */
    public ListPresetWindow() {
        this(new Stage());
    }

    /**
     * Shows the list preset window.
     *
     * @throws IllegalStateException If this method is called on a thread other than the JavaFX Application Thread.
     *                               If this method is called during animation or layout processing.
     *                               If this method is called on the primary stage.
     *                               If {@code dialogStage} is already showing.
     */
    public void show() {
        logger.fine("Showing the available packing list presets");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the window is currently being shown.
     *
     * @return the boolean
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Closes this window.
     */
    public void close() {
        getRoot().close();
    }
}
