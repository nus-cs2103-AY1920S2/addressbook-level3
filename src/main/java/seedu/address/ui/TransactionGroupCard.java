package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Transaction;

/**
 * A UI component that displays information of a group of {@code Transaction}.
 */
public class TransactionGroupCard extends UiPart<Region> {

    private static final int LIST_CELL_HEIGHT = 32;
    private static final int LIST_OFFSET = 0;

    private static final String FXML = "TransactionGroupCard.fxml";

    private static final String POSITIVE_CLASS = "positive";
    private static final String NEGATIVE_CLASS = "negative";

    public final FilteredList<Transaction> groupTransactionsList;

    @FXML
    private Label transactionGroupLabel;

    @FXML
    private Label groupExpenditureLabel;

    @FXML
    private ListView<Transaction> transactionItemsList;

    private int startIndex;

    public TransactionGroupCard(FilteredList<Transaction> groupTransactionsList, int startIndex) {
        super(FXML);
        this.groupTransactionsList = groupTransactionsList;
        this.startIndex = startIndex;

        setProperties();
        setGroupLabel();
        setGroupExpenditure();

        transactionItemsList.setItems(groupTransactionsList);
        transactionItemsList.setCellFactory(listView -> new TransactionListViewCell());
        transactionItemsList.setPrefHeight(groupTransactionsList.size() * LIST_CELL_HEIGHT + LIST_OFFSET);
    }

    private void setProperties() {
        transactionItemsList.setMouseTransparent(true);
        transactionItemsList.setFocusTraversable(false);
    }

    private void setGroupLabel() {
        Date groupDate = groupTransactionsList.get(0).getDate();
        transactionGroupLabel.setText(String.format("%s %s %s", groupDate.getLocalDate().getDayOfMonth(),
                groupDate.getMonth(), groupDate.getYear()));
    }

    private void setGroupExpenditure() {
        double groupValue;

        try {
            groupValue = groupTransactionsList
            .stream()
            .mapToLong(t -> {
                if (t instanceof Expense) {
                    return -t.getAmount().amountInCents;
                } else {
                    return t.getAmount().amountInCents;
                }
            })
            .reduce(0, Math::addExact);
        } catch (ArithmeticException e) {
            // Overflow occurs
            groupValue = Amount.MAX_VALUE;
        }

        if (groupValue < 0) {
            groupExpenditureLabel.setText("-" + new Amount(-groupValue / 100));
            groupExpenditureLabel.getStyleClass().add(NEGATIVE_CLASS);
        } else {
            groupExpenditureLabel.setText("+" + new Amount(groupValue / 100));
            groupExpenditureLabel.getStyleClass().add(POSITIVE_CLASS);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Transaction} using a {@code TransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {
        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (empty || transaction == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(new TransactionCard(transaction, startIndex).getRoot());
                        startIndex += 1;
                    }
                }
            });
        }
    }
}
