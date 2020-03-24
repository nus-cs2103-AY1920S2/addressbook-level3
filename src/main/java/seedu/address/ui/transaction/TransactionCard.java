package seedu.address.ui.transaction;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on InventorySystem level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customer;
    @FXML
    private Label product;
    @FXML
    private Label dateTime;
    @FXML
    private Label quantity;
    @FXML
    private Label money;
    @FXML
    private Label description;

    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex
                + ". "
                + transaction.getCustomer().getName().toString()
                + " bought "
                + transaction.getProduct().getDescription().toString());
        customer.setText(transaction.getCustomer().getName().toString());
        product.setText(transaction.getProduct().getDescription().toString());
        dateTime.setText(transaction.getDateTime().toString());
        quantity.setText(transaction.getQuantity().value);
        money.setText("$" + transaction.getMoney().value);
        description.setText(transaction.getDescription().value);
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

