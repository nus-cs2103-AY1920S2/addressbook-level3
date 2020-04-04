package seedu.expensela.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Transaction;

import javafx.scene.chart.PieChart;

/**
 * Panel containing the bar graph to break down expenditure according to category.
 */
public class ChartAnalyticsPanel extends UiPart<Region> {
    private static final String FXML = "ChartAnalyticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ChartAnalyticsPanel.class);

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart pieChart;

    public ChartAnalyticsPanel(ObservableList<Transaction> transactionList) {
        super(FXML);
        graphByWeek(transactionList);
        graphByCategory(transactionList);
    }

    private void graphByCategory(ObservableList<Transaction> transactionList) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList <String> categories = new ArrayList<>();
        ArrayList <Integer> amounts = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            String category = transaction.getCategory().toString();
            Amount amount = transaction.getAmount();
            double amountDouble = amount.transactionAmount;
            int amountInteger = (int) amountDouble;
            if (categories.contains(category)) {                    // category alr exists in categories
                int index = categories.indexOf(category);
                int newCategoryAmount = amountInteger + amounts.get(index);
                amounts.add(index, newCategoryAmount);
            } else {                                                // first time seeing the category
                categories.add(category);
                int index = categories.size() - 1;
                amounts.add(index, amountInteger);
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            pieChartData.add(new PieChart.Data(categories.get(i), amounts.get(i)));
        }
        pieChart.setData(pieChartData);
        pieChart.setTitle("Expense by Category");
    }

    /**
     * Creates a stacked bar chart, X axis is days of the week and Y axis is total expenditure, stacked by weeks.
     * @param transactionList Transaction List filtered by a certain month.
     */
    private void graphByWeek(ObservableList<Transaction> transactionList) {
        xAxis.setLabel("Day");
        xAxis.getCategories().addAll("Week 1", "Week 2", "Week 3", "Week 4", "Week 5");
        yAxis.setLabel("Spent");

        double[][] spentByWeekAndDay = new double[5][7];

        for (Transaction transaction : transactionList) {
            Amount amount = transaction.getAmount();
            LocalDate transactionLocalDate = transaction.getDate().transactionDate;
            int weekIndex = (transactionLocalDate.getDayOfMonth() - 1) / 7;
            DayOfWeek day = transactionLocalDate.getDayOfWeek();

            if (amount.positive) {
                continue;
            }

            switch (day.toString()) {
            case "MONDAY":
                spentByWeekAndDay[weekIndex][0] += amount.transactionAmount;
                break;
            case "TUESDAY":
                spentByWeekAndDay[weekIndex][1] += amount.transactionAmount;
                break;
            case "WEDNESDAY":
                spentByWeekAndDay[weekIndex][2] += amount.transactionAmount;
                break;
            case "THURSDAY":
                spentByWeekAndDay[weekIndex][3] += amount.transactionAmount;
                break;
            case "FRIDAY":
                spentByWeekAndDay[weekIndex][4] += amount.transactionAmount;
                break;
            case "SATURDAY":
                spentByWeekAndDay[weekIndex][5] += amount.transactionAmount;
                break;
            case "SUNDAY":
                spentByWeekAndDay[weekIndex][6] += amount.transactionAmount;
                break;
            default:
                continue;
            }
        }

        XYChart.Series<String, Number> seriesWeek1 = new XYChart.Series();
        seriesWeek1.setName("Week 1");
        XYChart.Series<String, Number> seriesWeek2 = new XYChart.Series();
        seriesWeek2.setName("Week 2");
        XYChart.Series<String, Number> seriesWeek3 = new XYChart.Series();
        seriesWeek3.setName("Week 3");
        XYChart.Series<String, Number> seriesWeek4 = new XYChart.Series();
        seriesWeek4.setName("Week 4");
        XYChart.Series<String, Number> seriesWeek5 = new XYChart.Series();
        seriesWeek5.setName("Week 5");

        String[] dayOfWeek = {"MON", "TUES", "WED", "THURS", "FRI", "SAT", "SUN"};
        for (int i = 0; i < 7; i++) {
            seriesWeek1.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[0][i]));
            seriesWeek2.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[1][i]));
            seriesWeek3.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[2][i]));
            seriesWeek4.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[3][i]));
            seriesWeek5.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[4][i]));
        }
        stackedBarChart.getData().addAll(seriesWeek1, seriesWeek2, seriesWeek3, seriesWeek4, seriesWeek5);
    }
}
