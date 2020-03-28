package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Transaction;

/**
 * Panel containing the bar graph to break down expenditure according to category.
 */
public class ChartAnalytics extends UiPart<Region> {
    private static final String FXML = "ChartAnalytics.fxml";
    private final Logger logger = LogsCenter.getLogger(ChartAnalytics.class);

    @FXML
    BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public ChartAnalytics(ObservableList<Transaction> transactionList) {
        super(FXML);
        xAxis.setLabel("Category");
        yAxis.setLabel("Total");
        
        double totalIncome = 0;
        double foodPrice = 0, shoppingPrice = 0, transportPrice = 0, groceriesPrice = 0, recreationPrice = 0,
                miscPrice = 0, utilitiesPrice = 0, rentPrice = 0;

        for (Transaction transaction : transactionList) {
            Category category = transaction.getCategory();
            Amount amount = transaction.getAmount();

            if (amount.positive) {
                totalIncome += amount.transactionAmount;
                continue;
            }

            switch (category.toString()) {
            case "FOOD":
                foodPrice += amount.transactionAmount;
                break;
            case "SHOPPING":
                shoppingPrice += amount.transactionAmount;
                break;
            case "TRANSPORT":
                transportPrice += amount.transactionAmount;
                break;
            case "GROCERIES":
                groceriesPrice += amount.transactionAmount;
                break;
            case "RECREATION":
                recreationPrice += amount.transactionAmount;
                break;
            case "MISC":
                miscPrice += amount.transactionAmount;
                break;
            case "UTILITIES":
                utilitiesPrice += amount.transactionAmount;
                break;
            case "RENT":
                rentPrice += amount.transactionAmount;
                break;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("Food", foodPrice));
        series.getData().add(new XYChart.Data<>("Shopping", shoppingPrice));
        series.getData().add(new XYChart.Data<>("Transport", transportPrice));
        series.getData().add(new XYChart.Data<>("Groceries", groceriesPrice));
        series.getData().add(new XYChart.Data<>("Recreation", recreationPrice));
        series.getData().add(new XYChart.Data<>("Misc", miscPrice));
        series.getData().add(new XYChart.Data<>("Utilities", utilitiesPrice));
        series.getData().add(new XYChart.Data<>("Rent", rentPrice));
        series.getData().add(new XYChart.Data<>("Income", totalIncome));

        barChart.getData().add(series);
    }
}
