package seedu.address.ui.admin;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.admin.Date;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays minimal information of a {@code Date}.
 */
public class DateCard extends UiPart<Region> {

    private static final String FXML = "DateListCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Date date;
    public final int numStudents;

    private HBox cardPane;
    @FXML
    private Label thisDate;
    @FXML
    private Label students;
    @FXML
    private Label id;

    /**
     * Constructor to create the date card controller.
     */
    public DateCard(Date date, int displayedIndex) {
        super(FXML);
        this.date = date;
        this.numStudents = date.getStudents().size();
        id.setText(displayedIndex + ". ");
        String fullDate = date.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
                + Integer.toString(date.getDate().getDayOfMonth()) + " " + Integer.toString(date.getDate().getYear());
        thisDate.setText("Date: " + fullDate);
        students.setText("Students: " + String.valueOf(this.numStudents));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateCard)) {
            return false;
        }

        // state check
        DateCard card = (DateCard) other;
        return id.getText().equals(card.id.getText())
                && date.equals(card.date);
    }
}
