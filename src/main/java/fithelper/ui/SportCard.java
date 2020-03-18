package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class SportCard extends UiPart<AnchorPane> {

    private static final String FXML = "SportCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Entry sport;

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

    public SportCard(Entry sport) {
        super(FXML);
        this.sport = sport;
        name.setText(sport.getName().toString());
        time.setText(sport.getTime().toString());
        place.setText(sport.getLocation().toString());
        calorie.setText(sport.getCalorie().toString());
    }

    /**
     * Creates a card displaying the {@code sport entry}.
     *
     * @param sport a list of sport entries
     * @param displayedIndex the index of the order to show on the card
     */
    public SportCard(Entry sport, int displayedIndex) {
        super(FXML);
        this.sport = sport;
        fillInDetails(sport, displayedIndex);
    }

    /**
     * Fills in details in GUI, in list view.
     * @param sport a list of sport entries
     * @param displayedIndex the index of the order to show on the card
     */
    private void fillInDetails(Entry sport, int displayedIndex) {
        index.setText(displayedIndex + ".");
        name.setText(sport.getName().toString());
        status.setText(sport.getStatus().toString());
        time.setText(sport.getTime().toString());
        place.setText(sport.getLocation().toString());
        calorie.setText(sport.getCalorie().toString());
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
