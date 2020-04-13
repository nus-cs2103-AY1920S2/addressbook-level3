package seedu.address.ui.facilitatorui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Facilitator}.
 */
public class FacilitatorCard extends UiPart<Region> {

    private static final String FXML = "FacilitatorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Facilitator facilitator;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardbox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label office;
    @FXML
    private FlowPane moduleCodes;

    public FacilitatorCard(Facilitator facilitator, int displayedIndex) {
        super(FXML);
        this.facilitator = facilitator;
        id.setText(displayedIndex + ". ");
        name.setText(facilitator.getName().fullName);

        if (facilitator.getPhone().value != null) {
            phone.setText(facilitator.getPhone().value);
        } else {
            cardbox.getChildren().remove(phone);
        }

        if (facilitator.getEmail().value != null) {
            email.setText(facilitator.getEmail().value);
        } else {
            cardbox.getChildren().remove(email);
        }

        if (facilitator.getOffice().value != null) {
            office.setText(facilitator.getOffice().value);
        } else {
            cardbox.getChildren().remove(office);
        }

        facilitator.getModuleCodes().stream()
                .sorted(Comparator.comparing(moduleCode -> moduleCode.value))
                .forEach(moduleCode -> moduleCodes.getChildren().add(new Label(moduleCode.value)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FacilitatorCard)) {
            return false;
        }

        // state check
        FacilitatorCard card = (FacilitatorCard) other;
        return id.getText().equals(card.id.getText())
                && facilitator.equals(card.facilitator);
    }
}
