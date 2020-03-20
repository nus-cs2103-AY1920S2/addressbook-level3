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
    private Label expenditureLabel;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label balanceLabel;

    public MonthlyDataPanel(MonthlyData monthlyData) {
        super(FXML);
        this.monthlyData = monthlyData;
        expenditureLabel.setText("Monthly Expense: ");
        budgetLabel.setText("Monthly Budget: ");
        incomeLabel.setText("Monthly Income: ");
        balanceLabel.setText("Total Balance: ");
    }

}

