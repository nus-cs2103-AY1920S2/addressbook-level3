package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class SchedulePage extends UiPart<Stage> {


    public static final String SCHEDULE_MESSAGE = "THIS IS MY PERSONAL SCHEDULE NOW BITCH";

    private static final Logger logger = LogsCenter.getLogger(SchedulePage.class);
    private static final String FXML = "SchedulePage.fxml";

    @FXML
    private Label scheduleMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public SchedulePage(Stage root) {
        super(FXML, root);
        scheduleMessage.setText(SCHEDULE_MESSAGE);
    }

    /**
     * Opens up the Schedule Page
     */
    public SchedulePage() {
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


}
