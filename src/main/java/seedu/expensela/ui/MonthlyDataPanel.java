package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.Balance;
import seedu.expensela.model.monthlydata.MonthlyData;

/**
 * Panel containing the list of transactions.
 */
public class MonthlyDataPanel extends UiPart<Region> {

    private static final String FXML = "MonthlyDataPanel.fxml";

    public final MonthlyData monthlyData;

    private final Logger logger = LogsCenter.getLogger(MonthlyDataPanel.class);

    @FXML
    private StackPane circleContainer;

    @FXML
    private Arc outer;

    @FXML
    private Arc outerOverlay;

    @FXML
    private Arc inner;

    @FXML
    private Label circleLabel;

    @FXML
    private Label balance;

    @FXML
    private Label budget;

    @FXML
    private Label expenditure;

    @FXML
    private Label income;

    /**
     * Create an instance of MonthlyData Panel with given monthlyData and balanceAmount
     * @param monthlyData to be displayed
     * @param balanceAmount to be displayed
     */
    public MonthlyDataPanel(MonthlyData monthlyData, Balance balanceAmount) {
        super(FXML);
        this.monthlyData = monthlyData;
        drawCircle(monthlyData.getBudget().budgetAmount, monthlyData.getExpense().expenseAmount);
        balance.setText("Total Balance: " + balanceAmount);
        budget.setText("Monthly Budget: " + monthlyData.getBudget());
        expenditure.setText("Monthly Expense: " + monthlyData.getExpense());
        income.setText("Monthly Income: " + monthlyData.getIncome());
    }

    /**
     * Draw circle object representation of ratio of expense and budget
     * @param budget
     * @param expense
     */
    private void drawCircle(double budget, double expense) {
        if (budget == 0 && expense == 0) {
            budget = 1;
            expense = 1;
        }
        double angle = 360 - expense / budget * 360;
        double overlayAngle = 0;
        if (angle < 0) {
            angle = 0;
        }
        Paint fill;
        Paint overlay = Color.web("#619196");
        if (angle > 180) {
            fill = Color.web("#BAFFC9");
        } else if (angle > 90) {
            fill = Color.web("FFFFBA");
        } else {
            overlayAngle = expense / budget * 360;
            angle = 100;
            fill = Color.web("#FFB3BA");
        }
        outer.setRadiusX(55);
        outer.setRadiusY(55);
        outer.setStartAngle(90);
        outer.setLength(angle);
        outer.setType(ArcType.ROUND);
        outer.setFill(fill);
        outerOverlay.setRadiusX(60);
        outerOverlay.setRadiusY(60);
        outerOverlay.setStartAngle(90);
        outerOverlay.setLength(-overlayAngle);
        outerOverlay.setType(ArcType.ROUND);
        outerOverlay.setFill(overlay);
        inner.setRadiusX(40);
        inner.setRadiusY(40);
        inner.setStartAngle(0);
        inner.setLength(360);
        inner.setType(ArcType.ROUND);
        inner.setFill(overlay);
        int percentage = 100 - (int) (expense * 100 / budget);
        if (percentage < 0) {
            percentage = 0;
        }
        String displayedPercentage = percentage + "%";
        circleLabel.setText(displayedPercentage);
    }

}

