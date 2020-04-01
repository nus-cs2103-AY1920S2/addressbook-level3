package csdev.couponstash.ui;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Savings summary of CouponStash.
 */
public class SummaryTab extends UiPart<Region> {
    private static final String FXML = "SummaryTab.fxml";

    private static final String SAVED_TOTAL_PRE_MESSAGE = "You saved a total of ";
    private static final String SAVEABLES_PRE_MESSAGE = "And these saveables too!";

    // Independent Ui parts residing in this Ui container
    private ObservableList<Coupon> allCoupons;
    private MoneySymbol moneySymbol;

    // Individual FXML components
    @FXML
    private Label savedText;
    @FXML
    private Label numericalAmount;
    @FXML
    private Label saveablesText;
    @FXML
    private BarChart<String, Number> savingsChart;
    @FXML
    private NumberAxis savingsAxis;
    @FXML
    private CategoryAxis daysAxis;
    @FXML
    private VBox allSaveables;


    public SummaryTab(ObservableList<Coupon> allCoupons, MoneySymbol moneySymbol) {
        super(FXML);
        this.allCoupons = allCoupons;
        this.moneySymbol = moneySymbol;
        savedText.setText(SummaryTab.SAVED_TOTAL_PRE_MESSAGE);
        saveablesText.setText(SummaryTab.SAVEABLES_PRE_MESSAGE);
        this.updateView();
    }

    public void updateView() {
        // set the graph
        XYChart.Series<String, Number> savingsPerWeek = new XYChart.Series<String, Number>();
        savingsChart.getData().add(savingsPerWeek);
        // sum up the total savings

        // set saveables

        // set the total amount
        this.numericalAmount.setText(this.moneySymbol.getString());
    }

    private void addToSaveables(PureMonetarySavings savingsSum) {
        savingsSum.getSaveables().ifPresent(saveablesList -> saveablesList.stream()
                .forEach(sva -> {
                    Label label = new Label(sva.getValue());
                    allSaveables.getChildren().add(label);
                }));
    }
}
