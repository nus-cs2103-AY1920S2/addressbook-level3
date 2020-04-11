package seedu.expensela.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Transaction;



/**
 * Panel containing the bar graph to break down expenditure according to category.
 */
public class ChartAnalyticsPanel extends UiPart<Region> {
    private static final String GraphByWeek_Fxml = "GraphByWeekPanel.fxml";
    private static final String GraphByMonth_Fxml = "GraphByMonthPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ChartAnalyticsPanel.class);

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart pieChart;

    public ChartAnalyticsPanel(ObservableList<Transaction> transactionList, boolean isFilterMonth) {
        super(chooseFxml(isFilterMonth));
        if (isFilterMonth) {
            graphByWeek(transactionList);
        } else {
            graphByMonth(transactionList);
        }
        graphByCategory(transactionList);
    }

    /**
     * Choose which FXML file to use based on whether filter is by month
     * @param isFilterMonth True if filter is by month
     * @return String of which FXML to use
     */
    private static String chooseFxml(boolean isFilterMonth) {
        if (isFilterMonth) {
            return GraphByWeek_Fxml;
        } else {
            return GraphByMonth_Fxml;
        }
    }

    /**
     * Creates a pie chart which displays the expenditure by category
     * @param transactionList
     */
    private void graphByCategory(ObservableList<Transaction> transactionList) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<String> categories = new ArrayList<String>();
        ArrayList<Double> amounts = new ArrayList<Double>();

        for (Transaction transaction : transactionList) {
            String category = transaction.getCategory().toString();
            if (category.equals("INCOME") || transaction.getAmount().positive) {
                continue;
            }
            Amount amount = transaction.getAmount();
            double amountDouble = amount.transactionAmount;
            if (categories.contains(category)) {
                int index = categories.indexOf(category);
                double newCategoryAmount = amountDouble + amounts.get(index);
                amounts.set(index, newCategoryAmount);
            } else {
                categories.add(category);
                int index = categories.size() - 1;
                amounts.add(index, amountDouble);
            }
        }

        double totalAmount = 0;
        for (int i = 0; i < categories.size(); i++) {
            pieChartData.add(new PieChart.Data(categories.get(i), amounts.get(i)));
            totalAmount += amounts.get(i);
        }

        double finalTotalAmount = totalAmount;
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ":\n",
                                String.format("%.1f%%", 100 * data.getPieValue() / finalTotalAmount))
                )
        );

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
        yAxis.setLabel("Total Expenditure");
        stackedBarChart.setTitle("Expenditure This Month by Day of the Week");

        double[][] spentByWeekAndDay = new double[5][7];

        for (Transaction transaction : transactionList) {
            Amount amount = transaction.getAmount();
            String categoryString = transaction.getCategory().toString();
            LocalDate transactionLocalDate = transaction.getDate().transactionDate;
            int weekIndex = (transactionLocalDate.getDayOfMonth() - 1) / 7;
            DayOfWeek day = transactionLocalDate.getDayOfWeek();

            if (amount.positive || categoryString.equals("INCOME")) {
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

    /**
     * Creates a stacked bar chart, X axis is months of the year and Y axis is total expenditure.
     * Stacks last year's and this year's transactions.
     * @param transactionList Transaction List filtered by a certain month.
     */
    private void graphByMonth(ObservableList<Transaction> transactionList) {
        xAxis.setLabel("Month");
        yAxis.setLabel("Total Expenditure");
        barChart.setTitle("Expenditure This Year and Last Year By Month");

        double[][] spentByYearAndMonth = new double[2][12];
        int currYear = LocalDate.now().getYear();

        for (Transaction transaction : transactionList) {
            Amount amount = transaction.getAmount();
            String categoryString = transaction.getCategory().toString();
            LocalDate transactionLocalDate = transaction.getDate().transactionDate;
            if (amount.positive || categoryString.equals("INCOME")) {
                continue;
            } else if (transactionLocalDate.getYear() < currYear - 1) {
                break;
            }

            int yearIndex = currYear - transactionLocalDate.getYear();
            int month = transactionLocalDate.getMonthValue();
            spentByYearAndMonth[yearIndex][month - 1] += amount.transactionAmount;
        }

        XYChart.Series<String, Number> seriesPrevYear = new XYChart.Series();
        seriesPrevYear.setName(currYear - 1 + "");
        XYChart.Series<String, Number> seriesCurrYear = new XYChart.Series();
        seriesCurrYear.setName(currYear + "");

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < 12; i++) {
            seriesPrevYear.getData().add(new XYChart.Data(months[i], spentByYearAndMonth[1][i]));
            seriesCurrYear.getData().add(new XYChart.Data(months[i], spentByYearAndMonth[0][i]));
        }

        barChart.getData().addAll(seriesPrevYear, seriesCurrYear);
    }
}
