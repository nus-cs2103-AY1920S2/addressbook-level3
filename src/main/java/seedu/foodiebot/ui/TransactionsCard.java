package seedu.foodiebot.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.foodiebot.model.transaction.PurchasedFood;

/**
 * A UI component that displays information of a {@code PurchasedFood}.
 */
public class TransactionsCard extends UiPart<Region> {
    private static final String FXML = "TransactionsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     */

    public final PurchasedFood food;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private ImageView iv;
    @FXML
    private Label id;
    @FXML
    private Label stallName;
    @FXML
    private Label price;
    @FXML
    private Label description;
    @FXML
    private Label datePurchased;
    @FXML
    private Label rating;
    @FXML
    private Label review;

    public TransactionsCard(PurchasedFood food, int displayedIndex) {
        super(FXML);
        this.food = food;
        id.setText(displayedIndex + ". ");
        iv.setImage(food.getFoodImage());
        name.setText(food.getName());
        price.setText("$" + food.getPrice());
        stallName.setText(food.getStallName());
        description.setText(food.getDescription());
        datePurchased.setText("Purchased: " + food.getDateAdded() + " at " + food.getTimeAdded());
        rating.setText("Rating: " + food.getRating().toString());
        review.setText("Review: " + food.getReview().toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionsCard)) {
            return false;
        }

        // state check
        TransactionsCard card = (TransactionsCard) other;
        return id.getText().equals(card.id.getText()) && food.equals(card.food);
    }

}
