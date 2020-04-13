package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Transaction;

/**
 * A UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {
    private static final String FXML = "TransactionCard.fxml";

    private static final String INCOME_CLASS = "positive";
    private static final String EXPENSE_CLASS = "negative";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label tag;
    @FXML
    private FlowPane tags;

    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;

        id.setText(displayedIndex + ". ");
        description.setText(transaction.getDescription().description);

        if (transaction instanceof Expense) {
            amount.setText("-" + String.format("%s", transaction.getAmount().toString()));
            amount.getStyleClass().add(EXPENSE_CLASS);
        } else {
            amount.setText("+" + String.format("%s", transaction.getAmount().toString()));
            amount.getStyleClass().add(INCOME_CLASS);
        }

        tag.setText(transaction.getTag().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
