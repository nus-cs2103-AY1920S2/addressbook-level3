package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Transaction;

/**
 * Panel containing the table of transactions.
 */
public class PersonTablePanel<T extends Transaction> extends UiPart<Region> {

    private static final String FXML = "PersonTablePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(PersonTablePanel.class);

    @FXML
    private TableView<T> transactionTableView;
    @FXML
    private TableColumn<T, String> indexTableColumn;
    @FXML
    private TableColumn<T, Description> descriptionTableColumn;
    @FXML
    private TableColumn<T, Amount> amountTableColumn;
    @FXML
    private TableColumn<T, String> dateTableColumn;

    private int index = -1;

    public PersonTablePanel(ObservableList<T> transactionList) {
        super(FXML);
        indexTableColumn.setCellValueFactory(data -> {
            index += 1;
            return new SimpleStringProperty(Integer.toString(index));
        });

        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<T, Description>("description"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<T, Amount>("amount"));
        dateTableColumn.setCellValueFactory(data -> {
            T transaction = data.getValue();
            Date transactionDate = transaction.getDate();
            return new SimpleStringProperty(
                    String.format("%s %s %s",
                            transactionDate.getLocalDate().getDayOfMonth(),
                            transactionDate.getMonth().toString().substring(0, 3),
                            transactionDate.getYear()));
        });
        transactionTableView.setItems(transactionList);

        setProperties();
    }

    private void setProperties() {
        indexTableColumn.setStyle("-fx-alignment: CENTER;");
        amountTableColumn.setStyle("-fx-alignment: CENTER;");
        dateTableColumn.setStyle("-fx-alignment: CENTER;");
    }
}
