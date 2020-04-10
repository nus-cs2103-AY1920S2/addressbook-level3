package team.easytravel.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import team.easytravel.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class StatusWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatusWindow.class);
    private static final String FXML = "StatusWindow.fxml";

    @FXML
    private Label scheduleMessage;
    @FXML
    private Label packingListMessage;
    @FXML
    private Label fixedExpenseMessage;
    @FXML
    private ProgressBar packingListIndicator;
    @FXML
    private Label accommodationMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatusWindow(Stage root) {
        super(FXML, root);
        root.setTitle("Check Status");
    }

    /**
     * Creates a new HelpWindow.
     */
    public StatusWindow() {
        this(new Stage());
    }

    /**
     * Shows the checkstatus window.
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
    public void show(List<String> status) {
        logger.fine("Showing the trip's status in preparedness");
        scheduleMessage.setText(status.get(0));
        fixedExpenseMessage.setText(status.get(2));
        accommodationMessage.setText(status.get(3));

        String packingList = status.get(1);
        String percentage = packingList.split(":")[2];
        double nominator = Double.parseDouble(percentage.split("/")[0]);
        double denominator = Double.parseDouble(percentage.split("/")[1]);
        packingListIndicator.getStylesheets().add("view/StatusWindow.css");

        if (denominator == 0.0) {
            packingListMessage.setText("[❗] There are no items in the packing list. "
                    + "You can add items to your packing list using the " + "\"additem\" command.");
        } else {
            packingListMessage.setText(status.get(1));
            packingListIndicator.setProgress(nominator / denominator);
        }
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Closes this window.
     */
    public void close() {
        getRoot().close();
    }

    /**
     * Returns true if the window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
