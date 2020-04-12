package seedu.recipe.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.recipe.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-t10-1.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "Summary of commands: "
            + "\n add - add a new recipe"
            + "\n edit - edit selected recipe"
            + "\n addStep - add steps to selected recipe"
            + "\n editStep - edit steps of selected recipe"
            + "\n deleteStep - delete steps of selected recipe"
            + "\n addIngredient - add ingredient selected to recipe"
            + "\n editIngredient - edit ingredient in selected recipe"
            + "\n deleteIngredient - delete ingredient in selected recipe"
            + "\n favourite - favourite recipes"
            + "\n unfavourite - unfavourite recipes"
            + "\n find - find recipe by name"
            + "\n filter - search for recipes by (multiple) criteria"
            + "\n delete - delete selected recipe"
            + "\n list - list all recipes"
            + "\n switch - switch tabs"
            + "\n clear - clear recipe book"
            + "\n plan - plan recipes"
            + "\n deletePlan - delete plans"
            + "\n clearPlan - clear all plans"
            + "\n groceryList - list all ingredients used in the planned recipes"
            + "\n listGoals - list the four main goals"
            + "\n deleteGoal - removes specified goal from specified recipe"
            + "\n cooked - mark a specific recipe as cooked"
            + "\n quote - to add your own quote"
            + "\n exit - to leave HYBB"
            + "\n For more information, please refer to our User Guide: \n " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
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
        if (getRoot().isIconified()) {
            getRoot().setIconified(false);
        }
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
