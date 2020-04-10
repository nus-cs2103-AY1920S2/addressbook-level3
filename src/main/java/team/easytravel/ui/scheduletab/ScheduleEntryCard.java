package team.easytravel.ui.scheduletab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.ui.UiPart;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class ScheduleEntryCard extends UiPart<Region> {

    private static final String FXML = "scheduletab/ScheduleEntryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final DayScheduleEntry scheduleEntry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label type;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label activityLocation;
    @FXML
    private ImageView imageViewType;

    public ScheduleEntryCard(DayScheduleEntry scheduleEntry, int displayedIndex) {
        super(FXML);
        this.scheduleEntry = scheduleEntry;
        id.setText(displayedIndex + ". ");
        title.setText(scheduleEntry.getTitle().value);
        type.setText(scheduleEntry.getType());
        startTime.setText("Start Time: " + scheduleEntry.getStartDateTime().toString());
        endTime.setText("End Time: " + scheduleEntry.getEndDateTime().toString());
        activityLocation.setText("Location: " + scheduleEntry.getLocation().value);

        Image imageType;

        if (scheduleEntry.getType().equals("Transport Booking")) {
            imageType = new Image("/images/plane.png");
        } else if (scheduleEntry.getType().equals("Activity")) {
            imageType = new Image("/images/activities.png");
        } else {
            imageType = new Image("/images/default.png");
        }

        imageViewType.setImage(imageType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleEntryCard)) {
            return false;
        }

        // state check
        ScheduleEntryCard card = (ScheduleEntryCard) other;
        return id.getText().equals(card.id.getText())
                && scheduleEntry.equals(card.scheduleEntry);
    }
}
