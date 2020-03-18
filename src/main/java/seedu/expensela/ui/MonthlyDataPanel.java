package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.monthlydata.MonthlyData;

/**
 * Panel containing the list of transactions.
 */
public class MonthlyDataPanel extends UiPart<Region> {
    private static final String FXML = "MonthlyDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MonthlyDataPanel.class);

    public final MonthlyData monthlyData;

    @FXML
    private Label expenditure;

    @FXML
    private Label budget;

    @FXML
    private Label income;

    @FXML
    private Label balance;

    public MonthlyDataPanel(MonthlyData monthlyData) {
        super(FXML);
        this.monthlyData = monthlyData;
        expenditure.setText("Monthly Expense: $2,000");
        budget.setText("Monthly Budget: $3,000");
        income.setText("Monthly Income: $5,000");
        balance.setText("Total Balance: $170,000");
    }

}

