package seedu.recipe.ui.plan;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.ui.UiPart;

/**
 * Controller for the grocery list window.
 */
public class GroceryListWindow extends UiPart<Stage> {

    private static String groceryListMessage = "";

    private static final Logger logger = LogsCenter.getLogger(GroceryListWindow.class);
    private static final String FXML = "plan/GroceryListWindow.fxml";

    @FXML
    private Button copyButton;
    @FXML
    private Label groceryList;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a new GroceryListWindow.
     * @param root Stage to use as the root of the GroceryListWindow.
     */
    public GroceryListWindow(Stage root, String groceries) {
        super(FXML, root);
        scrollPane.setFitToWidth(true);
        groceryList.setWrapText(true);
        root.setHeight(500);
        root.setWidth(300);
    }

    /**
     * Creates a new GroceryListWindow.
     */
    public GroceryListWindow(String groceries) {
        this(new Stage(), groceries);
    }

    public void setGroceryListMessage(String message) {
        groceryListMessage = message;
        groceryList.setText(message);
    }

    /**
     * Shows the grocery list window.
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
        logger.fine("Showing grocery list page.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the grocery list window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the grocery list window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the grocery list window.
     */
    public void focus() {
        if (getRoot().isIconified()) {
            getRoot().setIconified(false);
        }
        getRoot().requestFocus();
    }

    /**
     * Copies the grocery list to the clipboard.
     */
    @FXML
    private void copyGroceryList() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent groceryList = new ClipboardContent();
        groceryList.putString(groceryListMessage);
        clipboard.setContent(groceryList);
    }
}
