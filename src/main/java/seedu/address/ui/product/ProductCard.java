package seedu.address.ui.product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.product.Product;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Product}.
 */
public class ProductCard extends UiPart<Region> {

    private static final String FXML = "ProductListCard.fxml";

    private static final String RED_BAR = "red-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String GREEN_BAR = "green-bar";
    private static final String DISPLAY_CURRENCY = "$";
    private static final String[] barColorStyleClasses = { RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on InventorySystem level 4</a>
     */

    public final Product product;

    @FXML
    private HBox cardPaneProduct;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label costPrice;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private Label sales;
    @FXML
    private Label threshold;
    @FXML
    private ProgressBar progressBar;

    private double progress;

    public ProductCard(Product product, int displayedIndex) {
        super(FXML);
        this.product = product;
        id.setText(displayedIndex + ". ");
        description.setText(product.getDescription().value);
        costPrice.setText(DISPLAY_CURRENCY + product.getCostPrice().value);
        price.setText(DISPLAY_CURRENCY + product.getPrice().value);
        quantity.setText(String.valueOf(product.getQuantity().getValue()));
        sales.setText(DISPLAY_CURRENCY + product.getMoney().value);
        threshold.setText(product.getThreshold().toString());
        updateProgressBar();
    }

    /**
     * Updates the progress bar to visualise remaining stock quantity.
     */
    private void updateProgressBar() {
        progress = product.getQuantity().getValue() / (product.getThreshold().getDouble() * 5);
        product.setProgress(progress);
        progressBar.setProgress(progress);
        if (progress <= 0.2) {
            setBarStyleClass(progressBar, RED_BAR);
        } else if (progress <= 0.4) {
            setBarStyleClass(progressBar, ORANGE_BAR);
        } else if (progress <= 0.6) {
            setBarStyleClass(progressBar, YELLOW_BAR);
        } else {
            setBarStyleClass(progressBar, GREEN_BAR);
        }
    }

    private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
        bar.getStyleClass().removeAll(barColorStyleClasses);
        bar.getStyleClass().add(barStyleClass);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductCard)) {
            return false;
        }

        // state check
        ProductCard card = (ProductCard) other;
        return id.getText().equals(card.id.getText())
                && product.equals(card.product);
    }
}

