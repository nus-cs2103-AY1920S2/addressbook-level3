package seedu.expensela.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.expensela.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExpenseLa level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private Label remark;
    @FXML
    private Label category;

    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        name.setText(transaction.getName().transactionName);
        amount.setText(transaction.getAmount().toString());
        date.setText(transaction.getDate().transactionDate);
        remark.setText(transaction.getRemark().transactionRemark);
        category.setText(transaction.getCategory().transactionCategory);
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
