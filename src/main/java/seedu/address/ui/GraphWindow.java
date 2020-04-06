package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a graph page
 */
public class GraphWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GraphWindow.class);
    private static final String FXML = "GraphWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public GraphWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new GraphWindow.
     */
    public GraphWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * 
     * @throws IllegalStateException
     *                               <ul>
     *                               <li>if this method is called on a thread other
     *                               than the JavaFX Application Thread.</li>
     *                               <li>if this method is called during animation
     *                               or layout processing.</li>
     *                               <li>if this method is called on the primary
     *                               stage.</li>
     *                               <li>if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing graph page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the graph window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the grap window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the graph window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
