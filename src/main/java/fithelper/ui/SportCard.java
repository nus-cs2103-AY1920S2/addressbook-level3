package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class SportCard extends UiPart<Region> {

    private static final String FXML = "SportListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Entry sport;

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

    public SportCard(Entry sport, int i) {
        super(FXML);
        this.sport = sport;
        name.setText(sport.getName().toString());
        time.setText(sport.getTime().toString());
        location.setText(sport.getLocation().toString());
        calorie.setText(sport.getCalorie().toString());
        remark.setText(sport.getRemark().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SportCard)) {
            return false;
        }

        // state check
        SportCard card = (SportCard) other;
        return sport.equals(card.sport);
    }
}
