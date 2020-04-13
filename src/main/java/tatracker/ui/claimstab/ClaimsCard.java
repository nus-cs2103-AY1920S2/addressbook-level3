//@@author fatin99

package tatracker.ui.claimstab;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import tatracker.model.session.Session;
import tatracker.ui.UiPart;

/**
 * An UI component that displays information of a done {@code Session}.
 */
public class ClaimsCard extends UiPart<Region> {

    private static final String FXML = "ClaimsListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Session claim;

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

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public ClaimsCard(Session claim, int displayedIndex) {
        super(FXML);
        this.claim = claim;
        id.setText(displayedIndex + ". ");
        type.setText(claim.getSessionType().toString());
        date.setText(claim.getStartDateTime().format(dateFormat));
        time.setText(claim.getStartDateTime().format(timeFormat) + " - "
                + claim.getEndDateTime().format(timeFormat));
        module.setText(claim.getModuleCode().toUpperCase());
        description.setText(claim.getDescription());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClaimsCard)) {
            return false;
        }

        // state check
        ClaimsCard card = (ClaimsCard) other;
        return id.getText().equals(card.id.getText())
                && claim.equals(card.claim);
    }
}
