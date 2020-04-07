package csdev.couponstash.ui;

import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.Logic;
import csdev.couponstash.model.coupon.Coupon;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for a Coupon window
 */
public class CouponWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(CouponWindow.class);
    private static final String FXML = "CouponWindow.fxml";
    private CouponDetails couponDetails;

    @FXML
    private StackPane couponDetailsPlaceholder;

    /**
     * Creates a new CalendarWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    public CouponWindow(Stage root, Logic logic, Coupon coupon) {
        super(FXML, root);
        couponDetails = new CouponDetails(coupon);
        couponDetailsPlaceholder.getChildren().add(couponDetails.getRoot());
    }

    /**
     * Creates a new CouponWindow.
     */
    public CouponWindow(Logic logic) {
        this(new Stage(), logic);
    }


    /**
     * Shows the coupon window.
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
        logger.fine("Showing coupon's details.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the coupon window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the coupon window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the coupon window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
