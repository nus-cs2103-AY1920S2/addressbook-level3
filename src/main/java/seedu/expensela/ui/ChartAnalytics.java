package seedu.expensela.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Transaction;

/**
 * Panel containing the bar graph to break down expenditure according to category.
 */
public class ChartAnalytics extends UiPart<Region> {
    private static final String FXML = "ChartAnalytics.fxml";
    private final Logger logger = LogsCenter.getLogger(ChartAnalytics.class);

    @FXML
    StackedBarChart<String, Number> stackedBarChart;

    /*
    @FXML
    BarChart<String, Number> barChart;
*/
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public ChartAnalytics(ObservableList<Transaction> transactionList) {
        super(FXML);
        graphByWeek(transactionList);
        //graphByCategory(transactionList);
    }

    private void graphByWeek(ObservableList<Transaction> transactionList) {
        xAxis.setLabel("Day");
        xAxis.getCategories().addAll("Week 1", "Week 2", "Week 3", "Week 4");
        yAxis.setLabel("Spent");

        double[][] spentByWeekAndDay = new double[4][7];

        for (Transaction transaction : transactionList) {
            Amount amount = transaction.getAmount();
            LocalDate transactionLocalDate = transaction.getDate().transactionDate;
            int weekIndex = (transactionLocalDate.getDayOfMonth() - 1) / 7;
            DayOfWeek day = transactionLocalDate.getDayOfWeek();
/*
            if (amount.positive) {
                continue;
            }*/
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

        String[] dayOfWeek = {"MON", "TUES", "WED", "THURS", "FRI", "SAT", "SUN"};
        for (int i = 0; i < 7; i++) {
            seriesWeek1.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[0][i]));
            seriesWeek2.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[1][i]));
            seriesWeek3.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[2][i]));
            seriesWeek4.getData().add(new XYChart.Data(dayOfWeek[i], spentByWeekAndDay[3][i]));
        }
        stackedBarChart.getData().addAll(seriesWeek1, seriesWeek2, seriesWeek3, seriesWeek4);
    }
/*
    private void graphByCategory(ObservableList<Transaction> transactionList) {
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
    } */
}
