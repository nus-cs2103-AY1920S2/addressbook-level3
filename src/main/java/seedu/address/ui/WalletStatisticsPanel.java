package seedu.address.ui;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Transaction;

/**
 * Ui panel containing the statistics of the Wallet.
 */
public class WalletStatisticsPanel extends UiPart<Region> {

    private static final String FXML = "WalletStatisticsPanel.fxml";

    private static final String BUDGET_NOT_SET = "No budget set!";
    private static final String UNDER_BUDGET = "You are %s within your budget! Good job!";
    private static final String OVER_BUDGET = "You are %s over your budget! Oh no!";

    private static final String OVER_BUDGET_CLASS = "budget-over";
    private static final String UNDER_BUDGET_CLASS = "budget-under";

    private final Logger logger = LogsCenter.getLogger(WalletStatisticsPanel.class);

    @FXML
    private PieChart expenditurePieChart;

    @FXML
    private Label currentMonthYearLabel;

    @FXML
    private Label budgetRemainingLabel;

    @FXML
    private Label budgetOverUnderLabel;

    @FXML
    private VBox walletStatisticsLayout;

    @FXML
    private VBox walletStatisticsPlaceholder;

    private Budget budget;
    private ObservableList<Transaction> walletTransactionList;

    public WalletStatisticsPanel(Budget budget, ObservableList<Transaction> transactionList) {
        super(FXML);
        this.budget = budget;
        update(budget, transactionList);
        setProperties();
    }

    /**
     * Updates the statistics displayed with the modified {@code wallet} and {@code transactionList}.
     */
    public void update(Budget budget, ObservableList<Transaction> transactionList) {
        this.budget = budget;
        this.walletTransactionList = transactionList;

        if (transactionList.isEmpty()) {
            walletStatisticsLayout.setVisible(false);
            walletStatisticsPlaceholder.setVisible(true);
        } else {
            resetStyles();
            setCurrentMonthYear();
            populatePieChart();
            updateBudgetRemaining();
            walletStatisticsLayout.setVisible(true);
            walletStatisticsPlaceholder.setVisible(false);
        }


        for (Node node : expenditurePieChart.lookupAll(".chart-legend-item")) {
            if (node instanceof Label) {
                Label labelNode = (Label) node;
                labelNode.setWrapText(true);
                labelNode.setManaged(true);
                labelNode.setPrefWidth(100);
            }
        }
    }

    /**
     * Updates the current month / year label to the current date.
     */
    private void setCurrentMonthYear() {
        Date currDate = Date.getDefault();
        currentMonthYearLabel.setText(String.format("%s %s", currDate.getMonth(), currDate.getYear()));
    }

    /**
     * Populates the pie chart with the data provided.
     */
    private void populatePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Date currDate = Date.getDefault();

        HashMap<Tag, Amount> tagAmounts = new HashMap<Tag, Amount>();

        List<Transaction> expensesThisMonth = walletTransactionList
                .stream()
                .filter(t -> t instanceof Expense)
                .filter(t -> t.getDate().inMonth(currDate.getMonth(), currDate.getYear()))
                .collect(Collectors.toList());

        for (Transaction t : expensesThisMonth) {
            Tag tag = t.getTag();
            Amount amount = t.getAmount();
            tagAmounts.put(tag, tagAmounts.getOrDefault(tag, Amount.zero()).add(amount));
        }

        for (Map.Entry<Tag, Amount> entry : tagAmounts.entrySet()) {
            Tag tag = entry.getKey();
            Amount amount = entry.getValue();
            PieChart.Data tempData = new PieChart.Data(tag.tagName, (double) amount.amountInCents / 100);
            expenditurePieChart.layout();
            pieChartData.add(tempData);
        }

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(),
                String.format(" $%.2f", data.getPieValue()))));
        expenditurePieChart.setData(pieChartData);
    }

    /**
     * Updates the budget remaining after deducting the month's total expenditure.
     */
    private void updateBudgetRemaining() {
        Date currDate = Date.getDefault();
        Month currMonth = currDate.getMonth();
        Year currYear = currDate.getYear();

        if (budget.getAmount().isZero()) {
            budgetRemainingLabel.setText(BUDGET_NOT_SET);
            budgetOverUnderLabel.setVisible(false);
        } else {
            Amount totalExpenditure =
                    walletTransactionList
                            .stream()
                            .filter(t -> t instanceof Expense && t.getDate().inMonth(currMonth, currYear))
                            .map(Transaction::getAmount)
                            .reduce(Amount.zero(), Amount::add);

            Amount currBudgetAmount = budget.getAmount();

            budgetRemainingLabel.setText(totalExpenditure.toString() + " / " + currBudgetAmount.toString());

            if (totalExpenditure.isLessThan(currBudgetAmount)) {
                budgetOverUnderLabel.setText(String.format(UNDER_BUDGET,
                        currBudgetAmount.difference(totalExpenditure)));
                budgetRemainingLabel.getStyleClass().add(UNDER_BUDGET_CLASS);
            } else {
                budgetOverUnderLabel.setText(String.format(OVER_BUDGET,
                        currBudgetAmount.difference(totalExpenditure)));
                budgetRemainingLabel.getStyleClass().add(OVER_BUDGET_CLASS);
            }

            budgetOverUnderLabel.setVisible(true);
        }
    }

    private void resetStyles() {
        ObservableList<String> styleClass = budgetRemainingLabel.getStyleClass();
        styleClass.remove(OVER_BUDGET_CLASS);
        styleClass.remove(UNDER_BUDGET_CLASS);
    }

    private void setProperties() {
        walletStatisticsPlaceholder.managedProperty().bind(walletStatisticsPlaceholder.visibleProperty());
        walletStatisticsLayout.managedProperty().bind(walletStatisticsLayout.visibleProperty());

    }
}
