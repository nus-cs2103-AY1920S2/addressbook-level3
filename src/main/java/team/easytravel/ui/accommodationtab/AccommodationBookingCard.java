package team.easytravel.ui.accommodationtab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code Accommodation}.
 */
public class AccommodationBookingCard extends UiPart<Region> {

    private static final String FXML = "accommodationtab/AccommodationBookingCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final AccommodationBooking item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label accommodationName;
    @FXML
    private Label id;
    @FXML
    private Label startDay;
    @FXML
    private Label endDay;
    @FXML
    private Label accommodationLocation;
    @FXML
    private Label remark;

    public AccommodationBookingCard(AccommodationBooking item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");
        accommodationName.setText(item.getAccommodationName().toString());
        startDay.setText("Start Day: " + item.getStartDay().toString());
        endDay.setText("End Day: " + item.getEndDay().toString());
        //Called accommodationLocation because location is a reserved word in javafx.
        //Cannot create variable name called location/Location.
        accommodationLocation.setText("Location: " + item.getLocation().toString());
        remark.setText("Remark: " + item.getRemark().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AccommodationBookingCard)) {
            return false;
        }

        // state check
        AccommodationBookingCard card = (AccommodationBookingCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
