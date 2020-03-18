package fithelper.ui;

import fithelper.model.entry.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * An UI component that displays information of a {@code Entry}.
 */
public class ReminderCard extends UiPart<AnchorPane> {

    private static final String FXML = "ReminderCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Entry reminder;

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

    public ReminderCard(Entry reminder) {
        super(FXML);
        this.reminder = reminder;
        name.setText(reminder.getName().toString());
        time.setText(reminder.getTime().toString());
        place.setText(reminder.getLocation().toString());
        calorie.setText(reminder.getCalorie().toString());
    }

    /**
     * Creates a card displaying the {@code reminder entry}.
     *
     * @param reminder the list of reminder entries display
     * @param displayedIndex the index of the order to show on the card
     */
    public ReminderCard(Entry reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        fillInDetails(displayedIndex);
    }

    /**
     * Fills in details in GUI, in list view.
     * @param displayedIndex the index of the order to show on the card
     */
    private void fillInDetails(int displayedIndex) {
        index.setText(displayedIndex + ".");
        name.setText(reminder.getName().toString());
        status.setText(reminder.getStatus().toString());
        time.setText(reminder.getTime().toString());
        place.setText(reminder.getLocation().toString());
        calorie.setText(reminder.getCalorie().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return reminder.equals(card.reminder);
    }
}
