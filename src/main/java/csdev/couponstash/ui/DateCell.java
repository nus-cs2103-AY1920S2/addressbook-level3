package csdev.couponstash.ui;

import java.time.LocalDate;
import java.util.ArrayList;

import csdev.couponstash.model.coupon.Coupon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * An UI component that displays information of a calendar date in the calendar.
 */
public class DateCell extends UiPart<Region> {
    private static final String FXML = "DateCell.fxml";

    private LocalDate date;
    private ObservableList<Coupon> coupons;
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
    }

    /**
     * Returns the date of this {@code DateCell}.
     *
     * @return The date of this {@code DateCell}.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the text of this {@code DateCell}.
     *
     * @return The date of this {@code DateCell}.
     */
    public Text getText() {
        return displayText;
    }


    /**
     * Returns the number of Coupons in this {@code DateCell}.
     *
     */
    public int getNumberOfCoupons() {
        return coupons.size();
    }
}
