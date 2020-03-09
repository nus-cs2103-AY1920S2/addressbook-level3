package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class FoodCard extends UiPart<Region> {

    private static final String FXML = "FoodListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Entry food;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label time;
    @FXML
    private Label location;
    @FXML
    private Label calorie;
    @FXML
    private Label remark;

    public FoodCard(Entry food, int displayedIndex) {
        super(FXML);
        this.food = food;
        name.setText(food.getName().toString());
        time.setText(food.getTime().toString());
        location.setText(food.getLocation().toString());
        calorie.setText(food.getCalorie().toString());
        remark.setText(food.getRemark().value);
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
