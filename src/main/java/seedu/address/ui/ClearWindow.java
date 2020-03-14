package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Controller for a clear warning page
 */
public class ClearWindow extends UiPart<Stage> {
    public static final String WARNING_MESSAGE = "Are you sure you want to clear the order book list ?";

    private static final Logger logger = LogsCenter.getLogger(ClearWindow.class);
    private static Logic logic;
    private static ResultDisplay resultDisplay;
    private static final String FXML = "ClearWindow.fxml";

    @javafx.fxml.FXML
    private Button yesButton;
    private Button noButton;

    @FXML
    private Label warningMessage;

    /**
     * Creates a new ClearWindow.
     *
     * @param root Stage to use as the root of the ClearWindow.
     */
    public ClearWindow(Stage root) {
        super(FXML, root);
        warningMessage.setText(WARNING_MESSAGE);
    }

    /**
     * Creates a new ClearWindow.
     */
    public ClearWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
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
        logger.fine("Showing warning message to the user for clearing the order book list.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the clear window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the clear window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the clear window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    public void setComponent(ResultDisplay resultDisplay) {
        this.resultDisplay = resultDisplay;
    }

    /**
     * User press Yes for clearing the order book list. Clear the whole order book list
     * and display success message to user.
     */
    @FXML
    private void agreeToClearOrderBookList() {
        String commandText = "clear -f";
        try {
            CommandResult commandResult = logic.execute(commandText);
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
        hide();
    }

    /**
     * User press No for clearing the order book list.
     */
    @FXML
    private void disagreeToClearOrderBookList() {
        resultDisplay.setFeedbackToUser("");
        hide();
    }
}
