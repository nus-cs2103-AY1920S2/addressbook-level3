package seedu.address.ui.transaction;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of transactions.
 */
public class TransactionListPanel extends UiPart<Region> {
    private static final String FXML = "TransactionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private TableView<Transaction> transactionTableView;

    @FXML
    private TableColumn<Transaction, String> customerCol;

    @FXML
    private TableColumn<Transaction, String> productCol;

    @FXML
    private TableColumn<Transaction, String> dateTimeCol;

    @FXML
    private TableColumn<Transaction, String> quantityCol;

    @FXML
    private TableColumn<Transaction, String> amountCol;

    @FXML
    private TableColumn<Transaction, String> descriptionCol;

    public TransactionListPanel(ObservableList<Transaction> transactionList) {
        super(FXML);
        transactionTableView.setItems(transactionList);
        customerCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getCustomer().getName().toString());
            return property;
        });
        customerCol.setSortable(false);

        productCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getProduct().getDescription().toString());
            return property;
        });
        productCol.setSortable(false);

        dateTimeCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getDateTime().toString());
            return property;
        });
        dateTimeCol.setSortable(false);

        quantityCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getQuantity().toString());
            return property;
        });
        quantityCol.setSortable(false);

        amountCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getMoney().toString());
            return property;
        });
        amountCol.setSortable(false);

        descriptionCol.setCellValueFactory(t -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(t.getValue().getDescription().toString());
            return property;
        });
        descriptionCol.setSortable(false);
        logger.fine("Linked transaction list to panel.");
    }
}

