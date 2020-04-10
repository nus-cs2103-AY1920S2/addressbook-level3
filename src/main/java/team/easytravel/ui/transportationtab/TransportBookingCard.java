package team.easytravel.ui.transportationtab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TransportBookingCard extends UiPart<Region> {

    private static final String FXML = "transportationtab/TransportBookingCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final TransportBooking transportBooking;

    @FXML
    private HBox cardPane;
    @FXML
    private Label mode;
    @FXML
    private Label id;
    @FXML
    private Label startLocation;
    @FXML
    private Label endLocation;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView imageViewMode;

    public TransportBookingCard(TransportBooking transportBooking, int displayedIndex) {
        super(FXML);
        this.transportBooking = transportBooking;
        id.setText(displayedIndex + ". ");
        mode.setText(transportBooking.getMode().value);
        startLocation.setText("Start location: " + transportBooking.getStartLocation().value);
        endLocation.setText("End location: " + transportBooking.getEndLocation().value);
        startDateTime.setText("Start time: " + transportBooking.getStartDateTime().toString());
        endDateTime.setText("End time: " + transportBooking.getEndDateTime().toString());

        Image imageMode;

        if (transportBooking.getMode().value.equals("plane")) {
            imageMode = new Image("/images/plane.png");
        } else if (transportBooking.getMode().value.equals("bus")) {
            imageMode = new Image("/images/bus.png");
        } else if (transportBooking.getMode().value.equals("train")) {
            imageMode = new Image("/images/train.png");
        } else if (transportBooking.getMode().value.equals("car")) {
            imageMode = new Image("/images/car.png");
        } else {
            imageMode = new Image("/images/default.png");
        }

        imageViewMode.setImage(imageMode);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransportBookingCard)) {
            return false;
        }

        // state check
        TransportBookingCard card = (TransportBookingCard) other;
        return id.getText().equals(card.id.getText())
                && transportBooking.equals(card.transportBooking);
    }
}
