package seedu.address.ui.statistics;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Product}.
 */
public class StatisticsCard extends UiPart<Region> {

    private static final String FXML = "StatisticsListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on InventorySystem level 4</a>
     */

    public final Product product;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private Label quantity; // quantity of product since creation
    @FXML
    private Label sales;
    @FXML
    private Label costPrice;
    @FXML
    private Label profit;

    public StatisticsCard(Product product, int displayedIndex, List<Transaction> transactions) {
        super(FXML);
        this.product = product;
        id.setText(displayedIndex + ". ");
        description.setText(product.getDescription().value);
        costPrice.setText("$" + product.getCostPrice().value);
        price.setText("$" + product.getPrice().value);
        quantity.setText(String.valueOf(product.getQuantitySold(transactions)));
        sales.setText("$" + product.getMoney().value);
        profit.setText("$" + product.getProfit(transactions));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsCard)) {
            return false;
        }

        // state check
        StatisticsCard card = (StatisticsCard) other;
        return id.getText().equals(card.id.getText())
                && product.equals(card.product);
    }
}
