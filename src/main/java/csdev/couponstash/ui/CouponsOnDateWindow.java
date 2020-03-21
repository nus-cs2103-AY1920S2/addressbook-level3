package csdev.couponstash.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for the display window of the date.
 */
public class CouponsOnDateWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(csdev.couponstash.ui.CouponsOnDateWindow.class);
    private static final String FXML = "CouponsOnDateWindow.fxml";

    private Stage root;
    private LocalDate date;
    private CouponListPanel couponList;
    private ObservableList<Coupon> coupons;
    private String moneySymbol;

    @FXML
    private StackPane couponListPlaceholder;

    /**
     * Creates a new {@code CouponsOnDateWindow}.
     *
     * @param root Stage to use as the root of the {@code CouponsOnDateWindow}.
     */
    public CouponsOnDateWindow(Stage root, ObservableList<Coupon> coupons, String moneySymbol) {
        super(FXML, root);
        this.root = root;
        this.coupons = coupons;
        this.moneySymbol = moneySymbol;

        couponList = new CouponListPanel(coupons, moneySymbol);
        couponListPlaceholder.getChildren().add(couponList.getRoot());
    }

    /**
     * Creates a new {@code CouponsOnDateWindow}.
     */
    public CouponsOnDateWindow(ObservableList<Coupon> coupons, String moneySymbol) {
        this(new Stage(), coupons, moneySymbol);
    }

    /**
     * Sets the date to the specified {@LocalDate}.
     *
     * @param date The specified {@LocalDate}.
     */
    public void setDate(LocalDate date) {
        this.date = date;
        root.setTitle(getWindowTitle());
    }

    /**
     * Returns the title of the window of the date.
     *
     * @return Title of the window of the date.
     */
    private String getWindowTitle() {
        String title = String.format("Coupons expiring on %s",
                date.format(DateUtil.DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR));
        return title;
    }

    /**
     * Shows the display window for the date.
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
