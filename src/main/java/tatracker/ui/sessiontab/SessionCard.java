//@@author fatin99

package tatracker.ui.sessiontab;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import tatracker.model.session.Session;
import tatracker.ui.UiPart;

/**
 * An UI component that displays information of a {@code Session}.
 */
public class SessionCard extends UiPart<Region> {

    private static final String FXML = "SessionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Session session;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label module;
    @FXML
    private Label description;
    @FXML
    private Label recur;

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public SessionCard(Session session, int displayedIndex) {
        super(FXML);
        this.session = session;
        id.setText(displayedIndex + ". ");
        type.setText(session.getSessionType().toString());
        date.setText(session.getStartDateTime().format(dateFormat));
        time.setText(session.getStartDateTime().format(timeFormat) + " - "
                + session.getEndDateTime().format(timeFormat));
        module.setText(session.getModuleCode().toUpperCase());
        description.setText(session.getDescription());
        if (session.getRecurring() > 0) {
            recur.setText("Every " + session.getRecurring() + " Week(s)");
        } else {
            recur.setText("Not Recurring");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SessionCard)) {
            return false;
        }

        // state check
        SessionCard card = (SessionCard) other;
        return id.getText().equals(card.id.getText())
                && session.equals(card.session);
    }
}
