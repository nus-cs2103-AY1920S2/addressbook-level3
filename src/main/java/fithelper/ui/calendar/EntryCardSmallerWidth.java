package fithelper.ui.calendar;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class EntryCardSmallerWidth extends UiPart<AnchorPane> {

    private static final String FXML = "EntryCardSmallerWidth.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Entry entry;

    @FXML
    private AnchorPane innerPane;
    @FXML
    private Label index;
    @FXML
    private Text time;
    @FXML
    private Text place;
    @FXML
    private Text name;
    @FXML
    private HBox box1;
    @FXML
    private HBox box2;

    public EntryCardSmallerWidth(Entry entry) {
        super(FXML);
        this.entry = entry;
        name.setText(entry.getName().toString());
        time.setText(entry.getTime().toString());
        place.setText(entry.getLocation().toString());
    }

    /**
     * Creates a card displaying the {@code sport entry}.
     *
     * @param entry a list of sport entries
     * @param displayedIndex the index of the order to show on the card
     */
    public EntryCardSmallerWidth(Entry entry, int displayedIndex) {
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
        time.setText(entry.getTime().toString() + " - "
                + entry.getEndTime().toLocalTime().toString());
        place.setText(entry.getLocation().toString());
        if (entry.isDone()) {
            name.setStrikethrough(true);
            place.setStrikethrough(true);
            time.setStrikethrough(true);
            box1.setStyle("-fx-background-color:GREY; -fx-border-color:GREY");
            box2.setStyle("-fx-border-color:GREY");
            index.setStyle("-fx-background-color: GREY;");
            place.setFill(Color.GREY);
            time.setFill(Color.GREY);
        } else if (entry.isFood()) {
            box1.setStyle("-fx-background-color:#789cce; -fx-border-color:#789cce");
            box2.setStyle("-fx-border-color:#789cce");
            index.setStyle("-fx-background-color: #789cce;");
            place.setFill(Color.valueOf("#789cce"));
            time.setFill(Color.valueOf("#789cce"));
        } else {
            box1.setStyle("-fx-background-color:#ef827d; -fx-border-color:#ef827d");
            box2.setStyle("-fx-border-color:#ef827d");
            index.setStyle("-fx-background-color: #ef827d");
            place.setFill(Color.valueOf("#ef827d"));
            time.setFill(Color.valueOf("#ef827d"));
        }
        name.setFill(Color.WHITE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntryCard)) {
            return false;
        }

        // state check
        EntryCardSmallerWidth card = (EntryCardSmallerWidth) other;
        return entry.equals(card.entry);
    }
}
