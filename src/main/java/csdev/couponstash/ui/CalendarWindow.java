package csdev.couponstash.ui;

import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.model.coupon.Coupon;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class CalendarWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);
    private static final String FXML = "CalendarWindow.fxml";
    private ObservableList<Coupon> coupons;
    private CalendarPane calendarPane;

    @FXML
    private StackPane calendarPanePlaceholder;

    /**
     * Creates a new CalendarWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    public CalendarWindow(Stage root, ObservableList<Coupon> coupons) {
        super(FXML, root);
        calendarPane = new CalendarPane(coupons);
        calendarPanePlaceholder.getChildren().add(calendarPane.getRoot());
    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(ObservableList<Coupon> coupons) {
        this(new Stage(), coupons);
    }


    /**
     * Shows the Calendar window.
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
        logger.fine("Showing calendar.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the calendar window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the calendar window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the calendar window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
