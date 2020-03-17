package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class FoodCard extends UiPart<AnchorPane> {

    private static final String FXML = "FoodCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Entry food;

    @FXML
    private AnchorPane innerPane;

    @FXML
    private Label index;
    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label time;
    @FXML
    private Label place;
    @FXML
    private Label calorie;

    public FoodCard(Entry food) {
        super(FXML);
        this.food = food;
        name.setText(food.getName().toString());
        time.setText(food.getTime().toString());
        place.setText(food.getLocation().toString());
        calorie.setText(food.getCalorie().toString());
    }

    /**
     * Creates a card displaying the {@code food entry}.
     *
     * @param food the list of food entries display
     * @param displayedIndex the index of the order to show on the card
     */
    public FoodCard(Entry food, int displayedIndex) {
        super(FXML);
        this.food = food;
        fillInDetails(displayedIndex);
    }

    /**
     * Fills in details in GUI, in list view.
     * @param displayedIndex the index of the order to show on the card
     */
    private void fillInDetails(int displayedIndex) {
        index.setText(displayedIndex + ".");
        name.setText(food.getName().toString());
        status.setText(food.getStatus().toString());
        time.setText(food.getTime().toString());
        place.setText(food.getLocation().toString());
        calorie.setText(food.getCalorie().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FoodCard)) {
            return false;
        }

        // state check
        FoodCard card = (FoodCard) other;
        return food.equals(card.food);
    }
}
