package team.easytravel.ui.expensestab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code FixedExpense}.
 */
public class FixedExpenseCard extends UiPart<Region> {

    private static final String FXML = "expensestab/FixedExpenseCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final FixedExpense fixedExpense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label description;
    @FXML
    private Label category;
    @FXML
    private ImageView imageViewCategory;

    public FixedExpenseCard(FixedExpense fixedExpense, int displayedIndex) {
        super(FXML);
        this.fixedExpense = fixedExpense;
        id.setText(displayedIndex + ". ");
        description.setText(fixedExpense.getDescription().toString());
        amount.setText("Amount: $" + fixedExpense.getAmount().toString());
        category.setText("Category: " + fixedExpense.getFixedExpenseCategory().toString());

        Image imageCategory;

        if (fixedExpense.getFixedExpenseCategory().value.equals("activities")) {
            imageCategory = new Image("/images/activities.png");
        } else if (fixedExpense.getFixedExpenseCategory().value.equals("accommodations")) {
            imageCategory = new Image("/images/hotel.png");
        } else if (fixedExpense.getFixedExpenseCategory().value.equals("transport")) {
            imageCategory = new Image("/images/plane.png");
        } else {
            imageCategory = new Image("/images/default.png");
        }

        imageViewCategory.setImage(imageCategory);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FixedExpenseCard)) {
            return false;
        }

        // state check
        FixedExpenseCard card = (FixedExpenseCard) other;
        return id.getText().equals(card.id.getText())
                && fixedExpense.equals(card.fixedExpense);
    }
}
