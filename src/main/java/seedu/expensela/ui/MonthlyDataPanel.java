package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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
    private Arc inner;

    @FXML
    private Label balance;

    @FXML
    private Label budget;

    @FXML
    private Label expenditure;

    @FXML
    private Label income;

    public MonthlyDataPanel(MonthlyData monthlyData, Double balanceAmount) {
        super(FXML);
        this.monthlyData = monthlyData;
        Balance balanceModel = new Balance(balanceAmount.toString());
        drawCircle(monthlyData.getBudget().budgetAmount, monthlyData.getExpense().expenseAmount);
        balance.setText("Total Balance: " + balanceModel);
        budget.setText("Monthly Budget: " + monthlyData.getBudget());
        expenditure.setText("Monthly Expense: " + monthlyData.getExpense());
        income.setText("Monthly Income: " + monthlyData.getIncome());
    }

    private void drawCircle(double budget, double expense) {
        double angle = 360 - expense / budget * 360;
        if (angle < 0) {
            angle = 0;
        }
        Paint fill;
        if (angle > 180) {
            fill = Color.GREEN;
        } else if (angle > 90) {
            fill = Color.YELLOW;
        } else {
            fill = Color.RED;
        }
        outer.setCenterX(10);
        outer.setRadiusX(70);
        outer.setRadiusY(70);
        outer.setStartAngle(0);
        outer.setLength(angle);
        outer.setType(ArcType.ROUND);
        outer.setFill(fill);
        inner.setRadiusX(35);
        inner.setRadiusY(35);
        inner.setStartAngle(0);
        inner.setLength(360);
        inner.setType(ArcType.ROUND);
        inner.setFill(Color.BLACK);
    }

}

