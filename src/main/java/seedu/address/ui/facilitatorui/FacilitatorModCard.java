package seedu.address.ui.facilitatorui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Facilitator} for a module.
 */
public class FacilitatorModCard extends UiPart<Region> {
    private static final String FXML = "FacilitatorListCard.fxml";

    public final Facilitator facilitator;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private VBox cardbox;
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

    public FacilitatorModCard(Facilitator facilitator, int displayedIndex) {
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

        cardbox.setMinHeight(0);

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
        FacilitatorModCard card = (FacilitatorModCard) other;
        return id.getText().equals(card.id.getText())
                && facilitator.equals(card.facilitator);
    }
}
