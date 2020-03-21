package csdev.couponstash.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller for the display window of the date.
 */
public class CouponsOnDateWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(csdev.couponstash.ui.CouponsOnDateWindow.class);
    private static final String FXML = "CouponsOnDateWindow.fxml";

    private Stage root;
    private LocalDate date;

    @FXML
    private ListView<Coupon> couponListView;

    /**
     * Creates a new {@code CouponsOnDateWindow}.
     *
     * @param root Stage to use as the root of the {@code CouponsOnDateWindow}.
     */
    public CouponsOnDateWindow(Stage root) {
        super(FXML, root);
        this.root = root;
    }

    /**
     * Creates a new {@code CouponsOnDateWindow}.
     */
    public CouponsOnDateWindow(ObservableList<Coupon> coupons) {
        this(new Stage());
        couponListView.setItems(coupons);
        couponListView.setCellFactory(listView -> new CouponListViewCell());
    }


    /**
     * Sets the date to the specified {@LocalDate}.
     * @param date The specified {@LocalDate}.
     */
    public void setDate(LocalDate date) {
        this.date = date;
        root.setTitle(getWindowTitle());
    }

    /**
     * Returns the title of the window of the date.
     * @return Title of the window of the date.
     */
    private String getWindowTitle() {
        String title = String.format("Coupons expiring on %s",
                date.format(DateUtil.DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR));
        return title;
    }

    /**
     * {@code ListCell} that displays the {@code Coupon} using a {@code CouponCard}.
     */
    class CouponListViewCell extends ListCell<Coupon> {
        @Override
        protected void updateItem(Coupon coupon, boolean empty) {
            super.updateItem(coupon, empty);
            if (empty || coupon == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CouponCard(coupon, getIndex() + 1, "$").getRoot());
            }
        }
    }

    /**
     * Shows the display window for the date.
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
        logger.fine("Showing coupons display window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the display window of the day is shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the display window of the day.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the display window of the day.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
