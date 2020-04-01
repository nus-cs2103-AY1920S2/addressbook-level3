package csdev.couponstash.ui;

import java.time.LocalDate;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Savings summary of CouponStash.
 */
public class SummaryTab extends UiPart<Region> {
    private static final String FXML = "SummaryTab.fxml";

    private static final String SAVED_TOTAL_PRE_MESSAGE = "You saved a total of ";

    // Independent Ui parts residing in this Ui container
    private ObservableList<Coupon> allCoupons;
    private MoneySymbol moneySymbol;

    // Individual FXML components
    @FXML
    private Label savedText;
    @FXML
    private Label numericalAmount;
    @FXML
    private BarChart<String, Number> savingsChart;
    @FXML
    private NumberAxis savingsAxis;
    @FXML
    private CategoryAxis daysAxis;


    public SummaryTab(ObservableList<Coupon> allCoupons, MoneySymbol moneySymbol) {
        super(FXML);
        this.allCoupons = allCoupons;
        this.moneySymbol = moneySymbol;
        savedText.setText(SummaryTab.SAVED_TOTAL_PRE_MESSAGE);
        this.updateView();
    }

    public void updateView() {
        // set the graph


        // set the total amount
        this.numericalAmount.setText(this.moneySymbol.getString());
    }
}
