package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class SportsCard extends UiPart<Region> {

    private static final String FXML = "SportsListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Entry sports;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label time;
    @FXML
    private Label place;
    @FXML
    private Label calorie;
    @FXML
    private Label remark;

    public SportsCard(Entry sports) {
        super(FXML);
        this.sports = sports;
        name.setText(sports.getName().toString());
        time.setText(sports.getTime().toString());
        place.setText(sports.getLocation().toString());
        calorie.setText(sports.getCalorie().toString());
        remark.setText(sports.getRemark().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SportsCard)) {
            return false;
        }

        // state check
        SportsCard card = (SportsCard) other;
        return sports.equals(card.sports);
    }
}
