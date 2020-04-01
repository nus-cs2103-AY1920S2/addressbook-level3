package csdev.couponstash.ui;

import java.util.ArrayList;
import java.util.List;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;

import javafx.collections.FXCollections;
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

    private MonetaryAmount shownMonetaryAmount = new MonetaryAmount(0.0);


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
        DateSavingsSumMap mapOfAllCoupons = getMapOfAllCoupons();
        XYChart.Series<String, Number> savingsPerWeek = this.getSeries(mapOfAllCoupons);
        this.savingsChart.getData().add(savingsPerWeek);

        // sum up the total savings
        PureMonetarySavings totalSavings = mapOfAllCoupons.values().stream()
                .reduce(new PureMonetarySavings(),
                        PureMonetarySavings::add);

        // set saveables
        this.addToSaveables(totalSavings);

        // set the total amount
        this.shownMonetaryAmount = totalSavings.getMonetaryAmount().orElseThrow();
        this.updateTotalAmount();
    }

    private void addToSaveables(PureMonetarySavings savingsSum) {
        savingsSum.getSaveables().ifPresent(saveablesList -> saveablesList.stream()
                .forEach(sva -> {
                    Label label = new Label(sva.getValue());
                    allSaveables.getChildren().add(label);
                }));
    }

    private DateSavingsSumMap getMapOfAllCoupons() {
        DateSavingsSumMap map = new DateSavingsSumMap();
        this.allCoupons.stream()
                .map(Coupon::getSavingsMap)
                .forEach(map::addAll);
        System.out.println(map.size());
        return map;
    }

    private XYChart.Series<String, Number> getSeries(DateSavingsSumMap mapOfAllCoupons) {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        List<XYChart.Data<String, Number>> listOfData =
                new ArrayList<XYChart.Data<String, Number>>();
        mapOfAllCoupons.forEach((date, savings) ->
                listOfData.add(new XYChart.Data<String, Number>(
                        DateUtil.formatDate(date),
                        savings.getMonetaryAmountAsDouble()
                )));
        series.setData(FXCollections.observableList(listOfData));
        return series;
    }

    private void updateTotalAmount() {
        this.numericalAmount.setText(
                this.shownMonetaryAmount.getStringWithMoneySymbol(this.moneySymbol.getString()));
    }
}
