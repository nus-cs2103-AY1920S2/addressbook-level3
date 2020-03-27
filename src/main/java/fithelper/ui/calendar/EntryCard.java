package fithelper.ui.calendar;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class EntryCard extends UiPart<AnchorPane> {

    private static final String FXML = "EntryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Entry entry;

    @javafx.fxml.FXML
    private AnchorPane innerPane;
    @FXML
    private Label index;
    @FXML
    private Text time;
    @FXML
    private Text place;
    @FXML
    private Label calorie;
    @FXML
    private Text name;

    public EntryCard(Entry entry) {
        super(FXML);
        this.entry = entry;
        name.setText(entry.getName().toString());
        time.setText(entry.getTime().toString());
        place.setText(entry.getLocation().toString());
        calorie.setText(entry.getCalorie().toString());
    }

    /**
     * Creates a card displaying the {@code sport entry}.
     *
     * @param entry a list of sport entries
     * @param displayedIndex the index of the order to show on the card
     */
    public EntryCard(Entry entry, int displayedIndex) {
        super(FXML);
        this.entry = entry;
        fillInDetails(entry, displayedIndex);
    }

    /**
     * Fills in details in GUI, in list view.
     * @param entry a single entry
     * @param displayedIndex the index of the order to show on the card
     */
    private void fillInDetails(Entry entry, int displayedIndex) {
        index.setText(displayedIndex + ".");
        name.setText(entry.getName().toString());
        time.setText(entry.getTime().toString());
        place.setText(entry.getLocation().toString());
        calorie.setText(entry.getCalorie().toString());
        if (entry.isFood()) {
            calorie.setTextFill(Color.RED);
        } else {
            calorie.setTextFill(Color.GREEN);
        }
        name.setFill(Color.WHITE);
        place.setFill(Color.valueOf("#789cce"));
        time.setFill(Color.valueOf("#789cce"));
        if (entry.isDone()) {
            name.setStrikethrough(true);
            place.setStrikethrough(true);
            time.setStrikethrough(true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof fithelper.ui.calendar.EntryCard)) {
            return false;
        }

        // state check
        fithelper.ui.calendar.EntryCard card = (fithelper.ui.calendar.EntryCard) other;
        return entry.equals(card.entry);
    }
}
