package seedu.address.ui.product;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.product.Product;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class ProductListPanel extends UiPart<Region> {
    private static final String FXML = "ProductListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductListPanel.class);

    @FXML
    private ListView<Product> productListView;

    public ProductListPanel(ObservableList<Product> productList) {
        super(FXML);
        productListView.setItems(productList);
        logger.fine("Linked product list to panel.");
        productListView.setCellFactory(listView -> new ProductListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class ProductListViewCell extends ListCell<Product> {
        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProductCard(product, getIndex() + 1).getRoot());
            }
        }
    }

}
