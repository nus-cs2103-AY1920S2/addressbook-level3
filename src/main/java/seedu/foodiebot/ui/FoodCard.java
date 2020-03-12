package seedu.foodiebot.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.foodiebot.model.food.Food;

/**
 * An UI component that displays information of a {@code Food}.
 */
public class FoodCard extends UiPart<Region> {
    private static final String FXML = "FoodListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on
     * AddressBook level 4</a>
     */
    public final Food food;

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
    //@FXML
    //private Label cuisine;
    @FXML
    private Label description;

    public FoodCard(Food food, int displayedIndex) {
        super(FXML);
        this.food = food;
        //System.out.println("1" + food);
        id.setText(displayedIndex + ". ");
        //System.out.println("2" + displayedIndex);
        iv.setImage(food.getFoodImage());
        //System.out.println("3" + food.getFoodImage());
        name.setText(food.getName());
        //System.out.println("4" + food.getName());
        //System.out.println("5" + food.getStallName());
        stallName.setText(food.getStallName());
        //System.out.println("5" + food.getStallName());
        //cuisine.setText(food.getCuisine());
        description.setText(food.getDescription());
        //System.out.println("6" + food.getStallName());
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
        return id.getText().equals(card.id.getText()) && food.equals(card.food);
    }
}
