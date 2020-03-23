package seedu.address.ui.product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.product.Product;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Product}.
 */
public class ProductCard extends UiPart<Region> {

    private static final String FXML = "ProductListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on InventorySystem level 4</a>
     */

    public final Product product;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private Label sales;

    public ProductCard(Product product, int displayedIndex) {
        super(FXML);
        this.product = product;
        id.setText(displayedIndex + ". ");
        description.setText(product.getDescription().value);
        price.setText("Price: $"+ product.getPrice().value);
        quantity.setText("Quantity: " + product.getQuantity().value);
        sales.setText("Sales: $" + product.getSales().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.ui.product.ProductCard)) {
            return false;
        }

        // state check
        seedu.address.ui.product.ProductCard card = (seedu.address.ui.product.ProductCard) other;
        return id.getText().equals(card.id.getText())
                && product.equals(card.product);
    }
}

