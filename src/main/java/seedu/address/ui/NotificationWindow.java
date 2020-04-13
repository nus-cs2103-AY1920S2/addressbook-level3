package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;

/**
 * Controller for notification page.
 */
public class NotificationWindow extends UiPart<Stage> {

    public static final String NOTIFICATION_MESSAGE = "Product %s quantity low!\nRemaining balance: %s";

    private static final Logger logger = LogsCenter.getLogger(NotificationWindow.class);
    private static final String FXML = "NotificationWindow.fxml";

    @FXML
    private Label notificationMessage;

    /**
     * Creates a new NotificationWindow.
     *
     * @param root Stage to use as root of the NotificationWindow.
     */
    public NotificationWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new NotificationWindow.
     */
    public NotificationWindow() {
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
    public void show(Description description, Quantity quantity) {
        logger.fine("Showing notification window.");
        notificationMessage.setText(String.format(NOTIFICATION_MESSAGE, description, quantity));
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the notification window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the notification window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
