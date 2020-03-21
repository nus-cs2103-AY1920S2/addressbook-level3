package csdev.couponstash.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.Logic;
import csdev.couponstash.model.coupon.Coupon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * An UI component that displays information of a calendar date in the calendar.
 */
public class DateCell extends UiPart<Stage> {
    private static final String FXML = "DateCell.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private LocalDate date;
    private ObservableList<Coupon> coupons;
    private CouponsOnDateWindow couponsDisplayWindow;
    private Text displayText;
    private Circle displayCircle;

    @FXML
    private StackPane dateStackPane;

    /**
     * Constructs a calendar date cell.
     */
    public DateCell() {
        super(FXML);
        coupons = FXCollections.observableList(new ArrayList<>());
        couponsDisplayWindow = new CouponsOnDateWindow(coupons, "$");
        logger.info("Initializing new DateCell.");
    }

    /**
     * Returns the {@code StackPane} that is used to mount this {@code DateCell}.
     *
     * @return The {@code StackPane} that is used to mount this {@code DateCell}.
     */
    public StackPane getCalendarDateStackPane() {
        return dateStackPane;
    }

    public void setText(Text dateText) {
        displayText = dateText;
    }

    public void setCircle(Circle formattedCircle) {
        displayCircle = formattedCircle;
    }

    public void addChildren() {
        dateStackPane.getChildren().addAll(displayCircle, displayText);
    }


    /**
     * Adds the specified coupon to the list of coupons corresponding to this {@code DateCell}.
     *
     * @param coupon The specified coupon.
     */
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    /**
     * Clears all coupons from this {@code DateCell}.
     */
    public void clearCoupons() {
        coupons.clear();
    }

    /**
     * Sets the date of this {@code DateCell} to the specified date.
     *
     * @param date The specified date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
        couponsDisplayWindow.setDate(date);
    }

    /**
     * Returns the date of this {@code CalendarDateCell}.
     *
     * @return The date of this {@code CalendarDateCell}.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the number of Coupons in this {@code DateCell}.
     *
     */
    public int getNumberOfCoupons() {
        return coupons.size();
    }


    /**
     * Closes the displayed coupons list if it is opened.
     */
    public void closeDisplayedCoupons() {
        couponsDisplayWindow.hide();
    }


    /**
     * Displays the coupons for the day represented by this {@code DateCell}.
     */
    @FXML
    public void displayCoupons() {
        if (!couponsDisplayWindow.isShowing()) {
            couponsDisplayWindow.show();
        } else {
            couponsDisplayWindow.focus();
        }
    }

}
