package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
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
        balance.setText("Total Balance: " + balanceModel);
        budget.setText("Monthly Budget: " + monthlyData.getBudget());
        expenditure.setText("Monthly Expense: " + monthlyData.getExpense());
        income.setText("Monthly Income: " + monthlyData.getIncome());
    }

}

