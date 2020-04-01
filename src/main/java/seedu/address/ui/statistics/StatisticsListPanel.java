package seedu.address.ui.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class StatisticsListPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsListPanel.class);
    private static List<Transaction> transactions = new ArrayList<>();

    @FXML
    private ListView<Product> statisticsListView;

    public StatisticsListPanel(ObservableList<Product> productList, List<Transaction> transactions) {
        super(FXML);
        this.transactions = transactions;
        statisticsListView.setItems(productList);
        statisticsListView.setCellFactory(listView -> new StatisticsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code PersonCard}.
     */
    class StatisticsListViewCell extends ListCell<Product> {
        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StatisticsCard(product, getIndex() + 1, transactions).getRoot());
            }
        }
    }

}
